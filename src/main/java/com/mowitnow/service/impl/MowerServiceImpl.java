package com.mowitnow.service.impl;

import com.google.common.collect.Maps;
import com.mowitnow.domain.Mower;
import com.mowitnow.domain.Position;
import com.mowitnow.domain.type.Direction;
import com.mowitnow.dto.PositionFinalDto;
import com.mowitnow.mapper.MowerMapper;
import com.mowitnow.service.ApplicationParamService;
import com.mowitnow.service.MowerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of {@link MowerService}.
 *
 * @author Mazlum TOSUN
 * @see MowerService
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class MowerServiceImpl implements MowerService {

    @NonNull
    private final ApplicationParamService applicationParamService;
    private List<Mower> mowers;

    @PostConstruct
    private void init() {
        LOGGER.debug("Initializing mowers by application parameters...");
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
        val gardenHorizontalLimitMin = Integer.parseInt(applicationParamService.getGardenHorizontalLimitMin());
        val gardenHorizontalLimitMax = Integer.parseInt(applicationParamService.getGardenHorizontalLimitMax());
        val gardenVerticalLimitMin = Integer.parseInt(applicationParamService.getGardenVerticalLimitMin());
        val gardenVerticalLimitMax = Integer.parseInt(applicationParamService.getGardenVerticalLimitMax());

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

        final Map<Integer, Position> mowerCurrentPosition = Maps.newHashMap();
        mowerCurrentPosition.put(mower.getId(), mower.getPosition());

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
        final Position currentPosition = mowerCurrentPosition.get(mower.getId());
        final Position nextPosition = currentPosition
                .getOrientation()
                .moveMower(direction, currentPosition.getCoordinateX(), currentPosition.getCoordinateY());

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

        mowerCurrentPosition.put(mower.getId(), nextPosition);

        return Optional.of(mowerNextPosition.get());
    }

    private List<Mower> getMowers() {
        LOGGER.debug("Getting mowers...");
        return this.mowers;
    }
}
