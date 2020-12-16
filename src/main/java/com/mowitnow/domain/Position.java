package com.mowitnow.domain;

import com.mowitnow.domain.type.Orientation;

import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object that contains mower position data.
 *
 * @author Mazlum TOSUN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    private Integer coordinateX;
    private Integer coordinateY;
    private Orientation orientation;

    /**
     * Creates a new supplier of {@link Position} from the given X,Y coordinates and orientation.
     *
     * @param coordinateX X coordinate
     * @param coordinateY Y coordinate
     * @param orientation orientation
     * @return a supplier of position
     */
    public static Supplier<Position> of(final Integer coordinateX, final Integer coordinateY, final Orientation orientation) {
        return () -> new Position(coordinateX, coordinateY, orientation);
    }

    public void coordinateX(final String coordinateX) {
        setCoordinateX(Integer.valueOf(coordinateX));
    }

    public void coordinateY(final String coordinateY) {
        setCoordinateY(Integer.valueOf(coordinateY));
    }

    public void orientation(final String orientation) {
        setOrientation(Orientation.valueOf(orientation));
    }
}
