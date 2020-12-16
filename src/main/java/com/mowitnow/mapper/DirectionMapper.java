package com.mowitnow.mapper;

import com.mowitnow.constant.MowitnowConstant;
import com.mowitnow.domain.type.Direction;

import java.util.List;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

import static java.util.stream.Collectors.toList;

/**
 * Mapper that allows to transform object that concerns {@link Direction}.
 *
 * @author Mazlum TOSUN
 */
@UtilityClass
public class DirectionMapper {

    /**
     * Allows to transform the given directions parameters to a result directions list. Each
     * elements of result list contains a direction list (list of list). Each direction list
     * concerns a mower.
     *
     * @param directionsParams directions parameters
     * @return list where each element contains {@link Direction} list
     */
    public static List<List<Direction>> paramsToDirection(final String directionsParams) {
        return Pattern.compile(MowitnowConstant.MOWERS_SEPARATOR)
                .splitAsStream(directionsParams)
                .map(DirectionMapper::toDirection)
                .collect(toList());
    }

    /**
     * Allows to transform the given directions parameter to {@link Direction} list.
     *
     * @param directionsParam directions parameter
     * @return {@link Direction} direction list
     */
    private static List<Direction> toDirection(final String directionsParam) {
        return directionsParam.chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .map(Direction::valueOf)
                .collect(toList());
    }
}
