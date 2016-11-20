package com.mowitnow.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.domain.Mower;

/**
 * Allows to test treatments of {@link MowerMapper}.
 * 
 * @author Mazlum TOSUN
 */
public class MowerMapperTest extends AbstractTest {

  @Test
  public void whenBuildMowersByGivenParameters_ExpectSuccess() {

    // Tests data.
    final String directionsParams = "GAGAGAGAA,AADAADADDA";
    final int mowerNumber = directionsParams.split(MowitnowConstant.MOWERS_SEPARATOR).length;
    final String positionParams = "12N,33E";

    // Calls mower mapper by given parameters.
    final List<Mower> mowers =
        MowerMapper.INSTANCE.paramsToMowers(directionsParams, positionParams);

    // Asserts.
    assertThat(mowers).isNotNull().isNotEmpty().hasSize(mowerNumber);
  }
}
