package com.mowitnow.mapper;

import com.mowitnow.constant.MowitnowConstant;
import com.mowitnow.domain.Position;
import com.mowitnow.dto.PositionFinalDto;
import com.mowitnow.function.composition.PositionModifier;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that allows to transform object that concerns {@link PositionFinalDto}.
 *
 * @author Mazlum TOSUN
 */
@UtilityClass
public class PositionMapper {

    /**
     * Allows to transform the given positions parameters to {@link Position} list.
     *
     * @param positionParams position parameters
     * @return {@link Position} list
     */
    public static List<Position> paramsToPositions(final String positionParams) {
        return Pattern.compile(MowitnowConstant.MOWERS_SEPARATOR)
                .splitAsStream(positionParams)
                .map(PositionMapper::toPositions)
                .collect(toList());
    }

    /**
     * Allows to transform the given position string (parameter) to {@link Position}.
     *
     * @param positionParam current positions
     * @return {@link Position} position
     */
    private static Position toPositions(final String positionParam) {

        val index = new AtomicInteger(0);

        val position = new Position();

        positionParam.chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .forEach(field -> modifyPositionField(index.incrementAndGet(), field, position));

        return position;
    }

    /**
     * Allows to modify a field of the given {@link Position}. The goal is to associate an action to
     * an index. According to the index, a good setter of the given {@link Position}, will be
     * called.<br>
     * First character corresponds to X coordinate, the second corresponds to Y coordinate and the
     * last corresponds to orientation.<br>
     *
     * @param position current position
     */
    private static void modifyPositionField(final int index,
                                            final String positionField,
                                            final Position position) {
        PositionModifier.from(positionField)
                .modify(1, position::coordinateX)
                .modify(2, position::coordinateY)
                .modify(3, position::orientation)
                .execute(index);
    }
}
