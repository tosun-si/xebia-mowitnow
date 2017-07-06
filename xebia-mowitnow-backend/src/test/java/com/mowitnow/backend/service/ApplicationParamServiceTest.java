package com.mowitnow.backend.service;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.exception.ApplicationParamException;
import com.mowitnow.backend.service.impl.ApplicationParamServiceImpl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.function.Consumer;

import javax.inject.Inject;

import junitparams.Parameters;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Allows to test business treatments of {@link IApplicationParamService}.<br>
 * After class, spring context is refreshed, because in tests, application service is modified in
 * runtime for simulate some errors.
 *
 * @author Mazlum TOSUN
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ApplicationParamServiceTest extends AbstractTest {

    @Inject
    private ApplicationParamServiceImpl applicationParamService;

    private Object[] parametersForFailedValidations() {

        // Position.
        final Consumer<ApplicationParamServiceImpl> nullPosition = a -> a.setPosition(null);
        final Consumer<ApplicationParamServiceImpl> emptyPosition = a -> a.setPosition("");
        final Consumer<ApplicationParamServiceImpl> positionNbDifferentToDirections = a -> {
            a.setPosition("12N,33E");
            a.setDirections("GAGAGAGAA,AADAADADDA,GGADDADGDG");
        };

        // Direction.
        final Consumer<ApplicationParamServiceImpl> nullDirection = a -> a.setDirections(null);
        final Consumer<ApplicationParamServiceImpl> emptyDirection = a -> a.setDirections("");

        // Expected position.
        final Consumer<ApplicationParamServiceImpl> nullExpectedPosition = a -> a.setExpectedPositions(null);
        final Consumer<ApplicationParamServiceImpl> emptyExpectedPosition = a -> a.setExpectedPositions("");

        // Garden horizontal limit min.
        final Consumer<ApplicationParamServiceImpl> nullGardenHorizontalLimitMin = a -> a.setGardenHorizontalLimitMin(null);
        final Consumer<ApplicationParamServiceImpl> emptyGardenHorizontalLimitMin = a -> a.setGardenHorizontalLimitMin("");
        final Consumer<ApplicationParamServiceImpl> nonNumericGardenHorizontalLimitMin = a -> a.setGardenHorizontalLimitMin("GTC");

        // Garden horizontal limit max.
        final Consumer<ApplicationParamServiceImpl> nullGardenHorizontalLimitMax = a -> a.setGardenHorizontalLimitMax(null);
        final Consumer<ApplicationParamServiceImpl> emptyGardenHorizontalLimitMax = a -> a.setGardenHorizontalLimitMax("");
        final Consumer<ApplicationParamServiceImpl> nonNumericGardenHorizontalLimitMax = a -> a.setGardenHorizontalLimitMax("GTC");

        // Garden vertical limit min.
        final Consumer<ApplicationParamServiceImpl> nullGardenVerticalLimitMin = a -> a.setGardenVerticalLimitMin(null);
        final Consumer<ApplicationParamServiceImpl> emptyGardenVerticalLimitMin = a -> a.setGardenVerticalLimitMin("");
        final Consumer<ApplicationParamServiceImpl> nonNumericGardenVerticalLimitMin = a -> a.setGardenVerticalLimitMin("GTC");

        // Garden vertical limit max.
        final Consumer<ApplicationParamServiceImpl> nullGardenVerticalLimitMax = a -> a.setGardenVerticalLimitMax(null);
        final Consumer<ApplicationParamServiceImpl> emptyGardenVerticalLimitMax = a -> a.setGardenVerticalLimitMax("");
        final Consumer<ApplicationParamServiceImpl> nonNumericGardenVerticalLimitMax = a -> a.setGardenVerticalLimitMax("GTC");

        return new Object[][]{
                {nullPosition, "Positions parameters should not be empty"},
                {emptyPosition, "Positions parameters should not be empty"},
                {positionNbDifferentToDirections, "Positions length must be same to directions length"},

                {nullDirection, "Directions parameters should not be empty"},
                {emptyDirection, "Directions parameters should not be empty"},

                {nullExpectedPosition, "Expected positions parameters should not be empty"},
                {emptyExpectedPosition, "Expected positions parameters should not be empty"},

                {nullGardenHorizontalLimitMin, "Garden horizontal limit min should not be empty"},
                {emptyGardenHorizontalLimitMin, "Garden horizontal limit min should not be empty"},
                {nonNumericGardenHorizontalLimitMin, "Garden horizontal limit min should not be in type numeric"},

                {nullGardenHorizontalLimitMax, "Garden horizontal limit max should not be empty"},
                {emptyGardenHorizontalLimitMax, "Garden horizontal limit max should not be empty"},
                {nonNumericGardenHorizontalLimitMax, "Garden horizontal limit max should not be in type numeric"},

                {nullGardenVerticalLimitMin, "Garden vertical limit min should not be empty"},
                {emptyGardenVerticalLimitMin, "Garden vertical limit min should not be empty"},
                {nonNumericGardenVerticalLimitMin, "Garden vertical limit min should not be in type numeric"},

                {nullGardenVerticalLimitMax, "Garden vertical limit max should not be empty"},
                {emptyGardenVerticalLimitMax, "Garden vertical limit max should not be empty"},
                {nonNumericGardenVerticalLimitMax, "Garden vertical limit max should not be in type numeric"}};
    }

    @Test
    @Parameters(method = "parametersForFailedValidations")
    public void givenParamsWithError_whenValidateAppParameters_thenExceptionIsThrownWithExpectedMessage(
            final Consumer<ApplicationParamServiceImpl> failedAction, final String expectedMessage) {

        // Given.
        failedAction.accept(applicationParamService);

        // When.
        final ThrowingCallable action = () -> applicationParamService.init();

        // Then.
        assertThatThrownBy(action).isInstanceOf(ApplicationParamException.class).hasStackTraceContaining(expectedMessage);
    }
}
