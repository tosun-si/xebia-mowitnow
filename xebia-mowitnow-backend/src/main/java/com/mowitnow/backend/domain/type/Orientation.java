package com.mowitnow.backend.domain.type;


import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.helper.PositionHelper;

/**
 * Contains all possibles mower orientations (N, E, W, S).
 *
 * @author Mazlum TOSUN
 */
public enum Orientation {

    N {
        @Override
        public Position moveMower(final Direction direction, final int coordinateX, final int coordinateY) {

            return PositionHelper.INSTANCE.getPosition(direction,
                    Position.of(coordinateX, coordinateY, Orientation.W),
                    Position.of(coordinateX, coordinateY, Orientation.E),
                    Position.of(coordinateX, coordinateY + 1, Orientation.N));
        }
    },
    E {
        @Override
        public Position moveMower(final Direction direction, final int coordinateX, final int coordinateY) {

            return PositionHelper.INSTANCE.getPosition(direction,
                    Position.of(coordinateX, coordinateY, Orientation.N),
                    Position.of(coordinateX, coordinateY, Orientation.S),
                    Position.of(coordinateX + 1, coordinateY, Orientation.E));
        }
    },
    W {
        @Override
        public Position moveMower(final Direction direction, final int coordinateX, final int coordinateY) {

            return PositionHelper.INSTANCE.getPosition(direction,
                    Position.of(coordinateX, coordinateY, Orientation.S),
                    Position.of(coordinateX, coordinateY, Orientation.N),
                    Position.of(coordinateX - 1, coordinateY, Orientation.W));
        }
    },
    S {
        @Override
        public Position moveMower(final Direction direction, final int coordinateX, final int coordinateY) {

            return PositionHelper.INSTANCE.getPosition(direction,
                    Position.of(coordinateX, coordinateY, Orientation.E),
                    Position.of(coordinateX, coordinateY, Orientation.W),
                    Position.of(coordinateX, coordinateY - 1, Orientation.S));
        }
    };

    /**
     * Allows from the given direction and x/y coordinates, to move mower and returns new result
     * position. A {@link Position} contains new orientation (N,E,W,S) and new x/y coordinates.
     *
     * @param direction   direction
     * @param coordinateX x coordinate
     * @param coordinateY y coordinate
     * @return {@link Position} result position
     */
    public abstract Position moveMower(final Direction direction, final int coordinateX, final int coordinateY);
}
