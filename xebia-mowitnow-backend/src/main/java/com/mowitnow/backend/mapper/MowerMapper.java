package com.mowitnow.backend.mapper;

import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.domain.Mower;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;

import java.util.List;
import java.util.stream.IntStream;

import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that allows to transform object that concerns {@link Mower}.
 *
 * @author Mazlum TOSUN
 */
@UtilityClass
public class MowerMapper {

    /**
     * Allows to transform the given applications parameters to {@link Mower} list. These parameters
     * concerns mower number, directions and positions.
     *
     * @param directionsParams directions parameters
     * @param positionParams   position parameters
     * @return {@link Mower} mower list
     */
    public static List<Mower> paramsToMowers(final String directionsParams, final String positionParams) {

        // Gets mower directions and positions by application parameters.
        final List<List<Direction>> groupedDirections = DirectionMapper.paramsToDirection(directionsParams);
        final List<Position> positions = PositionMapper.paramsToPositions(positionParams);

        // Gets mower number. This number corresponds to directions or positions number.
        final int mowerNumber = directionsParams.split(MowitnowConstant.MOWERS_SEPARATOR).length;

        // Build and gets mowers by mower number. Directions and positions are in same order to mower.
        // We add each directions/positions to appropriate mower.
        return IntStream.range(0, mowerNumber)
                .mapToObj(n -> Mower.builder().id(n).position(positions.get(n)).directions(groupedDirections.get(n)).build())
                .collect(toList());
    }
}
