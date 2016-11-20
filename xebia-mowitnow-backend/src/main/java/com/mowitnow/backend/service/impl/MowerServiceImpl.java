package com.mowitnow.backend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import com.mowitnow.backend.domain.Mower;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;
import com.mowitnow.backend.dto.PositionFinalDto;
import com.mowitnow.backend.mapper.MowerMapper;
import com.mowitnow.backend.service.IApplicationParamService;
import com.mowitnow.backend.service.IMowerService;

/**
 * Implementation of {@link IMowerService}.
 * 
 * @see IMowerService
 * @author Mazlum TOSUN
 */
@Service
public class MowerServiceImpl implements IMowerService {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(MowerServiceImpl.class);

  // ----------------------------------------------
  // Fields
  // ----------------------------------------------

  @Inject
  private IApplicationParamService applicationParamService;

  private List<Mower> mowers;

  // ----------------------------------------------
  // Init
  // ----------------------------------------------

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

  // ----------------------------------------------
  // Public methods
  // ----------------------------------------------

  @Override
  public List<PositionFinalDto> getFinalPositions() {

    LOGGER.debug("Getting mowers final position...");

    // Transforms mowers to object that contains mower last positions.
    return this.getMowers().stream().map(this::getMowerFinalPosition).collect(Collectors.toList());
  }

  @Override
  public boolean checkIntoGarden(final Position position) {

    // Gets garden limits.
    final int gardenHorizontalLimitMin =
        Integer.valueOf(applicationParamService.getGardenHorizontalLimitMin());
    final int gardenHorizontalLimitMax =
        Integer.valueOf(applicationParamService.getGardenHorizontalLimitMax());
    final int gardenVerticalLimitMin =
        Integer.valueOf(applicationParamService.getGardenVerticalLimitMin());
    final int gardenVerticalLimitMax =
        Integer.valueOf(applicationParamService.getGardenVerticalLimitMax());

    // Checks if the given x/y coordinates are between 0 and 5, via streams.
    return IntStream.rangeClosed(gardenHorizontalLimitMin, gardenHorizontalLimitMax)
        .anyMatch(c -> c == position.getCoordinateX())
        && IntStream.rangeClosed(gardenVerticalLimitMin, gardenVerticalLimitMax)
            .anyMatch(c -> c == position.getCoordinateY());
  }

  // ----------------------------------------------
  // Private method
  // ----------------------------------------------

  /**
   * Factory method that allows to get final position of the given mower. Final position contains
   * x/y coordinate and orientation. A result is add in object that contains mower last position and
   * mower data.
   *
   * @return {@link PositionFinalDto} object that contains mower last position and mower data
   */
  private PositionFinalDto getMowerFinalPosition(final Mower mower) {

    LOGGER.debug("Getting mower [{}] final position...", mower.getId());

    // Puts initial position in result map. Correspond to initial position of mower.
    final Map<Integer, Position> finalPosition = Maps.newHashMap();
    finalPosition.put(mower.getId(), mower.getPosition());

    // Gets last element in stream. It corresponds to position of last direction (final position).
    return mower.getDirections().stream().map(d -> this.getNextPosition(mower, d, finalPosition))
        .reduce((d1, d2) -> d2).orElse(null);
  }

  /**
   * Factory method that allows to get next position of {@link Mower} by current position of mower,
   * current direction and coordinate X/Y.
   *
   * @param mower mower
   * @param direction direction
   * @param mowerPosition mower current position
   * @return {@link PositionFinalDto} object that contains mower next position and mower data
   */
  private PositionFinalDto getNextPosition(final Mower mower, final Direction direction,
      final Map<Integer, Position> mowerPosition) {

    // Gets last position of mower.
    final Position lastPosition = mowerPosition.get(mower.getId());

    // Gets new position by last position, current direction and x/y coordinates.
    final Position newPosition = lastPosition.getOrientation().moveMower(direction,
        lastPosition.getCoordinateX(), lastPosition.getCoordinateY());

    // We add next position if new x and y coordinates are in surface.
    final Optional<PositionFinalDto> nextPosition =
        Optional.of(newPosition).filter(this::checkIntoGarden)
            .map(p -> new PositionFinalDto.Builder().mower(mower).position(p).build());

    // If element is present and is in surface, we put position in map.
    nextPosition.ifPresent(p -> mowerPosition.put(mower.getId(), newPosition));

    // Returns optional that contains next position result.
    return nextPosition.orElse(null);
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
