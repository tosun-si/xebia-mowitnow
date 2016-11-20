package com.mowitnow.backend.helper;

import java.util.function.Supplier;

import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;
import com.mowitnow.backend.factory.PositionFactory;

/**
 * Helper that allows to get factory methods and do treatments on position object.
 * 
 * @author Mazlum TOSUN
 */
public enum PositionHelper {

  // Single instance.
  INSTANCE;

  // ----------------------------------------------
  // Public methods
  // ----------------------------------------------

  /**
   * Factory method that allows to get position by the given direction.<br>
   * All possible positions in suppliers are passed to this method, and are associated to
   * appropriate direction, via a factory.<br>
   * Then the good {@link Position} are recovered from the given direction.
   * 
   * @param direction current direction
   * @param leftPosition supplier that contains position object for left direction
   * @param rightPosition supplier that contains position object for right direction
   * @param advancePosition supplier that contains position object for advance direction
   * @return Position by the given direction
   */
  public Position getPosition(final Direction direction, final Supplier<Position> leftPosition,
      final Supplier<Position> rightPosition, final Supplier<Position> advancePosition) {

    // Associates all positions with appropriate direction via a factory and gets good position in
    // list by the given direction.
    return PositionFactory.builder().register(Direction.G, leftPosition)
        .register(Direction.D, rightPosition).register(Direction.A, advancePosition)
        .create(direction);
  }
}
