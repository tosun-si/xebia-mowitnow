package com.mowitnow.mapper;

import com.mowitnow.constant.MowitnowConstant;
import com.mowitnow.domain.Mower;
import com.mowitnow.domain.Position;
import com.mowitnow.domain.type.Direction;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

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
        final List<List<Direction>> groupedDirections = DirectionMapper.paramsToDirection(directionsParams);
        final List<Position> positions = PositionMapper.paramsToPositions(positionParams);
        final int mowerNumber = directionsParams.split(MowitnowConstant.MOWERS_SEPARATOR).length;

        return IntStream.range(0, mowerNumber)
                .mapToObj(n -> Mower.builder().id(n).position(positions.get(n)).directions(groupedDirections.get(n)).build())
                .collect(toList());
    }
}
