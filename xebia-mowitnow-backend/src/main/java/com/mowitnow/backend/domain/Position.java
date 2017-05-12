package com.mowitnow.backend.domain;

import com.mowitnow.backend.domain.type.Orientation;

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
   * Creates a new {@link Position} from the given X,Y coordinates and orientation.
   * 
   * @param coordinateX X coordinate
   * @param coordinateY Y coordinate
   * @param orientation orientation
   * @return Position
   */
  public static Position of(final Integer coordinateX, Integer coordinateY,
      Orientation orientation) {
    return new Position(coordinateX, coordinateY, orientation);
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
