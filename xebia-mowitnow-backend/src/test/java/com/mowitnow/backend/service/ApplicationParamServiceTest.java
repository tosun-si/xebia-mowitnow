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
  public void whenPositionParamIsNull_ExpectExceptionIsThrown() throws ApplicationParamException {

    // Given : overrides current position parameter to null in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setPosition(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Positions parameters should not be empty");
  }

  @Test
  public void whenDirectionsParamIsNull_ExpectExceptionIsThrown() throws ApplicationParamException {

    // Given : overrides current directions parameter to null in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setDirections(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Directions parameters should not be empty");
  }

  @Test
  public void whenPositionParamIsEmpty_ExpectExceptionIsThrown() throws ApplicationParamException {

    // Given : overrides current position parameter to null in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setPosition("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Positions parameters should not be empty");
  }

  @Test
  public void whenDirectionsParamIsEmpty_ExpectExceptionIsThrown()
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
  public void whenPositionNumberIsDifferentToDirections_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current position and directions parameter with position number different to
    // directions, in order to simulate an exception.
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
  public void wheneExpectedPositionIsNull_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setExpectedPositions(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Expected positions parameters should not be empty");
  }

  @Test
  public void wheneExpectedPositionIsEmpty_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setExpectedPositions("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Expected positions parameters should not be empty");
  }

  @Test
  public void whenGardenHorizontalLimitMinIsNull_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be empty");
  }

  @Test
  public void whenGardenHorizontalLimitMinIsEmpty_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be empty");
  }

  @Test
  public void whenGardenHorizontalLimitMinIsNotNumeric_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMin("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit min should not be in type numeric");
  }

  @Test
  public void whenGardenHorizontalLimitMaxIsNull_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax(null);

    // When
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be empty");
  }

  @Test
  public void whenGardenHorizontalLimitMaxIsEmpty_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be empty");
  }

  @Test
  public void whenGardenHorizontalLimitMaxIsNotNumeric_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenHorizontalLimitMax("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden horizontal limit max should not be in type numeric");
  }

  @Test
  public void whenGardenVerticalLimitMinIsNull_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be empty");
  }

  @Test
  public void whenGardenVerticalLimitMinIsEmpty_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given: overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be empty");
  }

  @Test
  public void whenGardenVerticalLimitMinIsNotNumeric_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMin("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit min should not be in type numeric");
  }

  @Test
  public void whenGardenVerticalLimitMaxIsNull_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax(null);

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be empty");
  }

  @Test
  public void whenGardenVerticalLimitMaxIsEmpty_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax("");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be empty");
  }

  @Test
  public void whenGardenVerticalLimitMaxIsNotNumeric_ExpectExceptionIsThrown()
      throws ApplicationParamException {

    // Given : overrides current parameter in order to simulate an error case.
    ((ApplicationParamServiceImpl) applicationParamService).setGardenVerticalLimitMax("GTG");

    // When.
    final ThrowingCallable action = () -> applicationParamService.init();

    // Then.
    assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class)
        .hasStackTraceContaining("Garden vertical limit max should not be in type numeric");
  }
}
