package com.mowitnow.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.domain.type.Orientation;
import com.mowitnow.backend.dto.PositionFinalDto;

import junitparams.Parameters;
import lombok.val;

/**
 * Allows to test business treatments of {@link IMowerService}.
 * 
 * @author Mazlum TOSUN
 */
public class MowerServiceTest extends AbstractTest {

  @Inject
  private IMowerService mowerService;
  @Inject
  private IApplicationParamService applicationParamService;

  private Object[] parametersCheckIntoGarden() {
    return new Object[][] {
            { 1, 2, Orientation.N, true },
            { 6, 2, Orientation.N, false },
            { 5, -1, Orientation.N, false },
            { 6, -1, Orientation.N, false } };
  }

  @Test
  public void givenAppParams_whenComputeLastPositionOfMowers_thenExpectedPositionInResult() {

    // Calls service.
    final List<PositionFinalDto> finalPositions = mowerService.getFinalPositions();

    // Asserts.
    assertThat(finalPositions).isNotNull().isNotEmpty();

    // Gets expected result positions in application parameters file.
    final String[] expectedPositions =
        applicationParamService.getExpectedPositions().split(MowitnowConstant.MOWERS_SEPARATOR);

    int index = 0;
    for (PositionFinalDto finalPosition : finalPositions) {

      // Given.
      final String expectedPosition = expectedPositions[index];
      final Orientation expectedOrientation =
          Orientation.valueOf(String.valueOf(expectedPosition.charAt(0)));
      final String expectedCoordinateX = String.valueOf(expectedPosition.charAt(1));
      final String expectedCoordinateY = String.valueOf(expectedPosition.charAt(2));

      // Then.
      assertThat(finalPosition).isNotNull();
      assertThat(finalPosition.getMower()).isNotNull();
      assertThat(finalPosition.getMower().getId()).isNotNull();
      assertThat(finalPosition.getPosition().getOrientation()).isNotNull()
          .isEqualTo(expectedOrientation);
      assertThat(finalPosition.getPosition().getCoordinateX().toString()).isNotNull()
          .isEqualTo(expectedCoordinateX);
      assertThat(finalPosition.getPosition().getCoordinateY().toString()).isNotNull()
          .isEqualTo(expectedCoordinateY);

      index++;
    }
  }

  @Test
  @Parameters(method = "parametersCheckIntoGarden")
  public void givenPosition_whenCheckIntoGarden_thenReturnExpectedResult(final Integer coordinateX,
      final Integer coordinateY, final Orientation orientation, final boolean expectedResult) {

    // Given.
    val position = new Position(coordinateX, coordinateY, orientation);

    // When.
    val isIntoGarden = mowerService.checkIntoGarden(position);

    // Then.
    assertThat(isIntoGarden).isEqualTo(expectedResult);
  }
}
