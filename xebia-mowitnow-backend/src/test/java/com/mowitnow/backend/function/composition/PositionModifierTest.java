package com.mowitnow.backend.function.composition;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.domain.Position;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import lombok.val;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Allows to test treatments of {@link PositionModifier}.
 *
 * @author Mazlum TOSUN
 */
public class PositionModifierTest extends AbstractTest {

    @Test
    public void givenValueAndGoodIndex_whenModifyPositionField_thenFieldIsCorrectlyUpdated() {

        // Given.
        val positionFieldValue = "8";
        val indexField = 2;

        // When.
        val position = new Position();
        PositionModifier.from(positionFieldValue)
                .modify(1, position::coordinateX)
                .modify(2, position::coordinateY)
                .execute(indexField);

        // Then.
        assertThat(position.getCoordinateY()).isNotNull().isEqualTo(Integer.valueOf(positionFieldValue));
    }

    @Test
    public void givenValueAndBadIndex_whenModifyPositionField_thenIllegalStateExceptionIsThrown() {

        // Given.
        val positionFieldValue = "8";
        val indexField = 3;

        // When.
        val position = new Position();
        final ThrowingCallable action = () -> PositionModifier.from(positionFieldValue)
                .modify(1, position::coordinateX)
                .modify(2, position::coordinateY)
                .execute(indexField);

        // Then.
        assertThatThrownBy(action).isInstanceOf(IllegalStateException.class).hasMessage("No action on person was found for index : " + indexField);
    }
}
