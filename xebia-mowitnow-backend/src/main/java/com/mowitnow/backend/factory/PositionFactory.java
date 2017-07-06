package com.mowitnow.backend.factory;

import com.google.common.collect.Maps;

import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;

import java.util.Map;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;

/**
 * Factory that allows build and get {@link Position} by a {@link Direction}.<br>
 * This factory associates some directions with position suppliers, with a fluent style method.<br>
 * Suppliers allows to instantiate person object only once in final method (lazy evaluation).
 *
 * @author Mazlum TOSUN
 */
@NoArgsConstructor
public final class PositionFactory {

    private final Map<Direction, Supplier<Position>> positions = Maps.newHashMap();

    /**
     * Static factory methods that allows to initialize factory.
     *
     * @return PositionFactory current factory
     */
    public static PositionFactory builder() {
        return new PositionFactory();
    }

    /**
     * Allows to register and associate a {@link Direction} with a {@link PositionFactory}, via a
     * fluent method.
     *
     * @param direction direction
     * @param position  position
     * @return PositionFactory current factory
     */
    public PositionFactory register(final Direction direction, final Supplier<Position> position) {
        this.positions.put(direction, position);
        return this;
    }

    /**
     * Final method that allows to create a {@link Position} object by the given {@link
     * Direction}.<br> This method find a position supplier that is associated to direction and
     * execute this supplier (lazy evaluation) in order to return final person object.
     *
     * @param direction current direction
     * @return Position position that corresponds to the given direction
     * @throws IllegalStateException if the given direction is not found in list (programming
     *                               error)
     */
    public Position create(final Direction direction) {

        // Gets position supplier in map that corresponds to the given direction and execute it.
        return this.positions.getOrDefault(direction, () -> {
            throw new IllegalStateException("Invalid direction : " + direction);
        }).get();
    }
}
