package com.mowitnow.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.mowitnow.backend.AbstractTest;

import lombok.val;

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
    val positions = PositionMapper.INSTANCE.paramsToPositions(positionParams);

    // Then.
    assertThat(positions).isNotNull().isNotEmpty().hasSize(2);
  }
}
