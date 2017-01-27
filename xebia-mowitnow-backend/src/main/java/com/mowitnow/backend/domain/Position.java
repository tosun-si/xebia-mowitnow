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

  // ----------------------------------------------
  // Fields
  // ----------------------------------------------

  private Integer coordinateX;
  private Integer coordinateY;
  private Orientation orientation;

  // ----------------------------------------------
  // Public methods
  // ----------------------------------------------

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
