package com.mowitnow.backend.service.impl;

import com.google.common.collect.Maps;

import com.mowitnow.backend.domain.Mower;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;
import com.mowitnow.backend.dto.PositionFinalDto;
import com.mowitnow.backend.mapper.MowerMapper;
import com.mowitnow.backend.service.IApplicationParamService;
import com.mowitnow.backend.service.IMowerService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of {@link IMowerService}.
 *
 * @author Mazlum TOSUN
 * @see IMowerService
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
        this.mowers = MowerMapper.paramsToMowers(applicationParamService.getDirections(), applicationParamService.getPosition());
    }

    @Override
    public List<PositionFinalDto> getFinalPositions() {

        LOGGER.debug("Getting mowers final position...");

        return this.getMowers().stream()
                .map(this::getFinalPosition)
                .collect(toList());
    }

    @Override
    public boolean isInGarden(final Position position) {

        // Gets garden limits.
        val gardenHorizontalLimitMin = Integer.valueOf(applicationParamService.getGardenHorizontalLimitMin());
        val gardenHorizontalLimitMax = Integer.valueOf(applicationParamService.getGardenHorizontalLimitMax());
        val gardenVerticalLimitMin = Integer.valueOf(applicationParamService.getGardenVerticalLimitMin());
        val gardenVerticalLimitMax = Integer.valueOf(applicationParamService.getGardenVerticalLimitMax());

        // Checks if the given x/y coordinates are between 0 and 5, via streams.
        return IntStream.rangeClosed(gardenHorizontalLimitMin, gardenHorizontalLimitMax).anyMatch(position.getCoordinateX()::equals)
                && IntStream.rangeClosed(gardenVerticalLimitMin, gardenVerticalLimitMax).anyMatch(position.getCoordinateY()::equals);
    }

    /**
     * Factory method that allows to get final position of the given mower. Final position contains
     * x/y coordinate and orientation. A result is add in object that contains mower last position
     * and mower data.
     *
     * @return {@link PositionFinalDto} object that contains mower last position and mower data
     */
    private PositionFinalDto getFinalPosition(final Mower mower) {

        LOGGER.debug("Getting mower [{}] final position...", mower.getId());

        // Puts initial position in result map. Correspond to initial position of mower.
        final Map<Integer, Position> mowerCurrentPosition = Maps.newHashMap();
        mowerCurrentPosition.put(mower.getId(), mower.getPosition());

        // Gets last element in stream. It corresponds to position of last direction (final position).
        return mower.getDirections().stream()
                .map(direction -> this.calculateNextPosition(mower, direction, mowerCurrentPosition))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce((d1, d2) -> d2)
                .get();
    }

    /**
     * Factory method that allows to calculate and get next position of {@link Mower} by current
     * position of mower, current direction and coordinate X/Y.
     *
     * @param mower                mower
     * @param direction            direction
     * @param mowerCurrentPosition mower current position
     * @return {@link PositionFinalDto} object that contains mower next position and mower data
     */
    private Optional<PositionFinalDto> calculateNextPosition(final Mower mower,
                                                             final Direction direction,
                                                             final Map<Integer, Position> mowerCurrentPosition) {

        // Gets current position of mower.
        final Position currentPosition = mowerCurrentPosition.get(mower.getId());

        // Moves mower and gets next position from current position, direction and x/y coordinates.
        final Position nextPosition = currentPosition.getOrientation().moveMower(direction, currentPosition.getCoordinateX(), currentPosition.getCoordinateY());

        // If the next position is in surface, the current position is updated with the next.
        return Optional.of(nextPosition)
                .filter(this::isInGarden)
                .flatMap(p -> buildNextPositionOfMower(mower, p, mowerCurrentPosition));
    }

    /**
     * Build an optional that contains the next position with the concerned mower.<br>
     * The given current position is updated with the new position (the mower entry is updated).
     */
    private Optional<PositionFinalDto> buildNextPositionOfMower(final Mower mower,
                                                                final Position nextPosition,
                                                                final Map<Integer, Position> mowerCurrentPosition) {

        final Supplier<PositionFinalDto> mowerNextPosition = () -> PositionFinalDto.builder()
                .mower(mower)
                .position(nextPosition)
                .build();

        // Updates the current position with the new.
        mowerCurrentPosition.put(mower.getId(), nextPosition);

        return Optional.of(mowerNextPosition.get());
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
