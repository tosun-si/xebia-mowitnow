package com.mowitnow.backend.domain;

import com.mowitnow.backend.domain.type.Direction;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Object that contains mower data.
 *
 * @author Mazlum TOSUN
 */
@Builder
@Getter
public class Mower {

    private final Integer id;
    private final Position position;
    private final List<Direction> directions;
}
