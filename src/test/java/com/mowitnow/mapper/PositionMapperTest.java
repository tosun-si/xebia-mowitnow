package com.mowitnow.mapper;

import com.mowitnow.AbstractTest;

import org.junit.Test;

import lombok.val;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Allows to test treatments of {@link PositionMapper}.
 *
 * @author Mazlum TOSUN
 */
public class PositionMapperTest extends AbstractTest {

    @Test
    public void givenPositionParams_whenMapToPositions_thenCorrectResultList() {

        // Given.
        val positionParams = "12N,33E";

        // When.
        val positions = PositionMapper.paramsToPositions(positionParams);

        // Then.
        assertThat(positions).isNotNull().isNotEmpty().hasSize(2);
    }
}
