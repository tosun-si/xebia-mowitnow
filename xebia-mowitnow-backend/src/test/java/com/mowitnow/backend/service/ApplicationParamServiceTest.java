package com.mowitnow.backend.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.inject.Inject;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.exception.ApplicationParamException;
import com.mowitnow.backend.service.impl.ApplicationParamServiceImpl;

/**
 * Allows to test business treatments of {@link IApplicationParamService}.<br>
 * After class, spring context is refreshed, because in tests, application service is modified in
 * runtime for simulate some errors.
 *
 * @author Mazlum TOSUN
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ApplicationParamServiceTest extends AbstractTest {

  // ----------------------------------------------
  // Fields
  // ----------------------------------------------

  @Inject
  private ApplicationParamServiceImpl applicationParamService;

  // ----------------------------------------------
  // Tests
  // ----------------------------------------------

  @Test
  public void givenNullPositionParam_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current position parameter to null in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setPosition(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Positions parameters should not be empty");
  }

  @Test
  public void givenNullDirectionsParam_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setDirections(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Directions parameters should not be empty");
  }

  @Test
  public void givenEmptyPositionParam_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setPosition("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Positions parameters should not be empty");
  }

  @Test
  public void givenEmptyDirectionsParam_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current directions parameter to null in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setDirections("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Directions parameters should not be empty");
  }

  @Test
  public void givenPositionNumberDifferentToDirections_whenValidateAppParameters_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setPosition("12N,33E");
    ((ApplicationParamServiceImpl) applicationParamService)
        .setDirections("GAGAGAGAA,AADAADADDA,GGADDADGDG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Positions length must be same to directions length");
  }

  @Test
  public void givenNullExpectedPosition_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setExpectedPositions(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Expected positions parameters should not be empty");
  }

  @Test
  public void givenEmptyExpectedPosition_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setExpectedPositions("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Expected positions parameters should not be empty");
  }

  @Test
  public void givenNullGardenHorizontalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be empty");
  }

  @Test
  public void givenEmptyGardenHorizontalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be empty");
  }

  @Test
  public void givenNonNumericGardenHorizontalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be in type numeric");
  }

  @Test
  public void givenNullGardenHorizontalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax(null);

    // When
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be empty");
  }

  @Test
  public void givenEmptyGardenHorizontalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be empty");
  }

  @Test
  public void givenNonNumericGardenHorizontalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be in type numeric");
  }

  @Test
  public void givenNullGardenVerticalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be empty");
  }

  @Test
  public void givenEmptyGardenVerticalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be empty");
  }

  @Test
  public void givenNonNumericGardenVerticalLimitMin_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be in type numeric");
  }

  @Test
  public void givenNullGardenVerticalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be empty");
  }

  @Test
  public void givenEmptyGardenVerticalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be empty");
  }

  @Test
  public void givenNonNumericGardenVerticalLimitMax_whenValidateAppParameters_thenExceptionIsThrown()
      throws ApplicationParamException {

    // Given.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be in type numeric");
  }
}
