package com.mowitnow.backend.function.composition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.domain.Position;

/**
 * Allows to test treatments of {@link PositionModifier}.
 * 
 * @author Mazlum TOSUN
 */
public class PositionModifierTest extends AbstractTest {

  @Test
  public void whenModifyPositionFieldWithExistIndex_ExpectSuccess() {

    // Given.
    final String positionFieldValue = "8";
    final int indexField = 2;

    // When.
    final Position position = new Position();
    PositionModifier.from(positionFieldValue).modify(1, position::coordinateX)
        .modify(2, position::coordinateY).execute(indexField);

    // Then.
    assertThat(position.getCoordinateY()).isNotNull()
        .isEqualTo(Integer.valueOf(positionFieldValue));
  }

  @Test
  public void whenModifyPositionFieldWithNotExistIndex_ExpectIllegalStateExceptionWasThrown() {

    // Given.
    final String positionFieldValue = "8";
    final int indexField = 3;

    // When.
    final Position position = new Position();
    final ThrowingCallable action = () -> PositionModifier.from(positionFieldValue)
        .modify(1, position::coordinateX).modify(2, position::coordinateY).execute(indexField);

    // Then.
    assertThatThrownBy(action).isInstanceOf(IllegalStateException.class)
        .hasMessage("No action on person was found for index : " + indexField);
  }
}
