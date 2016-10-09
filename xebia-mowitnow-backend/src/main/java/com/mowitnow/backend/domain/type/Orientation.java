package com.mowitnow.backend.domain.type;


import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.helper.PositionHelper;

/**
 * Contains all possibles mower orientations (N, E, W, S).
 * 
 * @author Mazlum TOSUN
 */
public enum Orientation {

  // ----------------------------------------------
  // Enumeration
  // ----------------------------------------------

  N {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Applies all possible positions and gets the good position in list by the given direction.
      return PositionHelper.INSTANCE.getPosition(direction,
          () -> new Position(coordinateX, coordinateY, Orientation.W),
          () -> new Position(coordinateX, coordinateY, Orientation.E),
          () -> new Position(coordinateX, coordinateY + 1, Orientation.N));
    }
  },
  E {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Applies all possible positions and gets the good position in list by the given direction.
      return PositionHelper.INSTANCE.getPosition(direction,
          () -> new Position(coordinateX, coordinateY, Orientation.N),
          () -> new Position(coordinateX, coordinateY, Orientation.S),
          () -> new Position(coordinateX + 1, coordinateY, Orientation.E));
    }
  },
  W {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Applies all possible positions and gets the good position in list by the given direction.
      return PositionHelper.INSTANCE.getPosition(direction,
          () -> new Position(coordinateX, coordinateY, Orientation.S),
          () -> new Position(coordinateX, coordinateY, Orientation.N),
          () -> new Position(coordinateX - 1, coordinateY, Orientation.W));
    }
  },
  S {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Applies all possible positions and gets the good position in list by the given direction.
      return PositionHelper.INSTANCE.getPosition(direction,
          () -> new Position(coordinateX, coordinateY, Orientation.E),
          () -> new Position(coordinateX, coordinateY, Orientation.W),
          () -> new Position(coordinateX, coordinateY - 1, Orientation.S));
    }
  };

  // ----------------------------------------------
  // Abstract method
  // ----------------------------------------------

  /**
   * Allows from the given direction and x/y coordinates, to build and returns new result position.
   * A {@link Position} contains new orientation (N,E,W,S) and new x/y coordinates.
   * 
   * @param direction direction
   * @param coordinateX x coordinate
   * @param coordinateY y coordinate
   * @return {@link Position} result position
   */
  public abstract Position getNewPosition(final Direction direction, final int coordinateX,
      final int coordinateY);
}
