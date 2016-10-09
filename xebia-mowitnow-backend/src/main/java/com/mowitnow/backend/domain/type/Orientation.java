package com.mowitnow.backend.domain.type;

import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.factory.PositionFactory;

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

      // Build and returns a map with all possible positions after applying the given direction.
      return PositionFactory.builder()
          .register(Direction.G, () -> new Position(coordinateX, coordinateY, Orientation.W))
          .register(Direction.D, () -> new Position(coordinateX, coordinateY, Orientation.E))
          .register(Direction.A, () -> new Position(coordinateX, coordinateY + 1, Orientation.N))
          .create(direction);
    }
  },
  E {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Build and returns a map with all possible positions after applying the given direction.
      return PositionFactory.builder()
          .register(Direction.G, () -> new Position(coordinateX, coordinateY, Orientation.N))
          .register(Direction.D, () -> new Position(coordinateX, coordinateY, Orientation.S))
          .register(Direction.A, () -> new Position(coordinateX + 1, coordinateY, Orientation.E))
          .create(direction);
    }
  },
  W {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Build and returns a map with all possible positions after applying the given direction.
      return PositionFactory.builder()
          .register(Direction.G, () -> new Position(coordinateX, coordinateY, Orientation.S))
          .register(Direction.D, () -> new Position(coordinateX, coordinateY, Orientation.N))
          .register(Direction.A, () -> new Position(coordinateX - 1, coordinateY, Orientation.W))
          .create(direction);
    }
  },
  S {
    @Override
    public Position getNewPosition(final Direction direction, final int coordinateX,
        final int coordinateY) {

      // Build and returns a map with all possible positions after applying the given direction.
      return PositionFactory.builder()
          .register(Direction.G, () -> new Position(coordinateX, coordinateY, Orientation.E))
          .register(Direction.D, () -> new Position(coordinateX, coordinateY, Orientation.W))
          .register(Direction.A, () -> new Position(coordinateX, coordinateY - 1, Orientation.S))
          .create(direction);
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
