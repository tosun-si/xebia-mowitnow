package com.mowitnow.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.domain.Position;

/**
 * Allows to test treatments of {@link PositionMapper}.
 * 
 * @author Mazlum TOSUN
 */
public class PositionMapperTest extends AbstractTest {

  @Test
  public void whenBuildPositionsByGivenParameters_ExpectSuccess() {

    // Given.
    final String positionParams = "12N,33E";

    // When.
    final List<Position> positions = PositionMapper.INSTANCE.paramsToPositions(positionParams);

    // Then.
    assertThat(positions).isNotNull().isNotEmpty().hasSize(2);
  }
}
