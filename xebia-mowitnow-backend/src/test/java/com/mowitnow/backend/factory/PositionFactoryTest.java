package com.mowitnow.backend.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Direction;
import com.mowitnow.backend.domain.type.Orientation;

/**
 * Allows to test treatments of {@link PositionFactory}.
 * 
 * @author Mazlum TOSUN
 */
public class PositionFactoryTest extends AbstractTest {

  @Test
  public void whenCreatePositionFromFactoryWithExistDirection_ExpectObjectIsCreated() {

    // Given, when.
    final Position resultPosition =
        PositionFactory.builder().register(Direction.D, () -> new Position(2, 5, Orientation.E))
            .register(Direction.A, () -> new Position(2, 5, Orientation.E)).create(Direction.D);

    // Then.
    assertThat(resultPosition).isNotNull();
    assertThat(resultPosition.getCoordinateX()).isNotNull().isEqualTo(2);
    assertThat(resultPosition.getCoordinateY()).isNotNull().isEqualTo(5);
    assertThat(resultPosition.getOrientation()).isNotNull().isEqualTo(Orientation.E);
  }

  @Test
  public void whenCreatePositionFromFactoryWithNoExistDirection_ExpectExceptionIsThrown() {

    // Given, when.
    final ThrowingCallable action = () -> PositionFactory.builder()
        .register(Direction.D, () -> new Position(2, 5, Orientation.E))
        .register(Direction.A, () -> new Position(2, 5, Orientation.E)).create(null);

    // Then.
    assertThatThrownBy(action).isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Invalid direction :");
  }
}
