package com.mowitnow.helper;

import com.mowitnow.domain.Position;
import com.mowitnow.domain.type.Direction;
import com.mowitnow.factory.PositionFactory;

import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

/**
 * Helper that allows to get factory methods and do treatments on position object.
 *
 * @author Mazlum TOSUN
 */
@UtilityClass
public class PositionHelper {

    /**
     * Factory method that allows to get position by the given direction.<br>
     * All possible positions in suppliers are passed to this method, and are associated to
     * appropriate direction, via a factory.<br>
     * Then the good {@link Position} are recovered from the given direction.
     *
     * @param direction       current direction
     * @param leftPosition    supplier that contains position object for left direction
     * @param rightPosition   supplier that contains position object for right direction
     * @param advancePosition supplier that contains position object for advance direction
     * @return Position by the given direction
     */
    public static Position getPosition(final Direction direction,
                                       final Supplier<Position> leftPosition,
                                       final Supplier<Position> rightPosition,
                                       final Supplier<Position> advancePosition) {

        return PositionFactory.builder()
                .register(Direction.G, leftPosition)
                .register(Direction.D, rightPosition)
                .register(Direction.A, advancePosition)
                .create(direction);
    }
}
