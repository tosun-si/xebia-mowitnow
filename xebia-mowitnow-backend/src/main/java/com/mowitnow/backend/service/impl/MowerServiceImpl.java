package com.mowitnow.backend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import com.mowitnow.backend.domain.Mower;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;
import com.mowitnow.backend.dto.PositionFinalDto;
import com.mowitnow.backend.mapper.MowerMapper;
import com.mowitnow.backend.service.IApplicationParamService;
import com.mowitnow.backend.service.IMowerService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link IMowerService}.
 * 
 * @see IMowerService
 * @author Mazlum TOSUN
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class MowerServiceImpl implements IMowerService {

  @NonNull
  private final IApplicationParamService applicationParamService;

  private List<Mower> mowers;

  /**
   * Allows to initializes mowers.
   */
  @PostConstruct
  private void init() {
    LOGGER.debug("Initializing mowers by application parameters...");

    // Gets mowers by application parameters (directions, positions).
    this.mowers = MowerMapper.INSTANCE.paramsToMowers(this.applicationParamService.getDirections(),
        this.applicationParamService.getPosition());
  }

  @Override
  public List<PositionFinalDto> getFinalPositions() {

    LOGGER.debug("Getting mowers final position...");

    // Transforms mowers to object that contains mower last positions.
    return this.getMowers().stream().map(this::getFinalPosition).collect(Collectors.toList());
  }

  @Override
  public boolean checkIntoGarden(final Position position) {

    // Gets garden limits.
    val gardenHorizontalLimitMin =
        Integer.valueOf(applicationParamService.getGardenHorizontalLimitMin());
    val gardenHorizontalLimitMax =
        Integer.valueOf(applicationParamService.getGardenHorizontalLimitMax());
    val gardenVerticalLimitMin =
        Integer.valueOf(applicationParamService.getGardenVerticalLimitMin());
    val gardenVerticalLimitMax =
        Integer.valueOf(applicationParamService.getGardenVerticalLimitMax());

    // Checks if the given x/y coordinates are between 0 and 5, via streams.
    return IntStream.rangeClosed(gardenHorizontalLimitMin, gardenHorizontalLimitMax)
        .anyMatch(position.getCoordinateX()::equals)
        && IntStream.rangeClosed(gardenVerticalLimitMin, gardenVerticalLimitMax)
            .anyMatch(position.getCoordinateY()::equals);
  }

  /**
   * Factory method that allows to get final position of the given mower. Final position contains
   * x/y coordinate and orientation. A result is add in object that contains mower last position and
   * mower data.
   *
   * @return {@link PositionFinalDto} object that contains mower last position and mower data
   */
  private PositionFinalDto getFinalPosition(final Mower mower) {

    LOGGER.debug("Getting mower [{}] final position...", mower.getId());

    // Puts initial position in result map. Correspond to initial position of mower.
    final Map<Integer, Position> initialPosition = Maps.newHashMap();
    initialPosition.put(mower.getId(), mower.getPosition());

    // Gets last element in stream. It corresponds to position of last direction (final position).
    return mower.getDirections().stream()
        .map(d -> this.calculateNextPosition(mower, d, initialPosition)).reduce((d1, d2) -> d2)
        .get();
  }

  /**
   * Factory method that allows to calculate and get next position of {@link Mower} by current
   * position of mower, current direction and coordinate X/Y.
   *
   * @param mower mower
   * @param direction direction
   * @param mowerPosition mower current position
   * @return {@link PositionFinalDto} object that contains mower next position and mower data
   */
  private PositionFinalDto calculateNextPosition(final Mower mower, final Direction direction,
      final Map<Integer, Position> mowerPosition) {

    // Gets current position of mower.
    final Position currentPosition = mowerPosition.get(mower.getId());

    // Moves mower and gets next position from current position, direction and x/y coordinates.
    final Position nextPosition = currentPosition.getOrientation().moveMower(direction,
        currentPosition.getCoordinateX(), currentPosition.getCoordinateY());

    // Checks if the next position is in the garden and build an object that associates mower to the
    // next position.
    final Optional<PositionFinalDto> nextPositionInGarden =
        Optional.of(nextPosition).filter(this::checkIntoGarden)
            .map(p -> PositionFinalDto.builder().mower(mower).position(p).build());

    // If element is present and is in surface, we put position in map.
    nextPositionInGarden.ifPresent(p -> mowerPosition.put(mower.getId(), nextPosition));

    // Returns optional that contains next position. If next position isn't in the garden, we return
    // current position.
    return nextPositionInGarden
        .orElseGet(() -> PositionFinalDto.builder().mower(mower).position(currentPosition).build());
  }

  /**
   * Allows to get {@link Mower} list in application.
   * 
   * @return {@link Mower} list
   */
  private List<Mower> getMowers() {
    LOGGER.debug("Getting mowers...");

    // Returns mowers.
    return this.mowers;
  }
}
