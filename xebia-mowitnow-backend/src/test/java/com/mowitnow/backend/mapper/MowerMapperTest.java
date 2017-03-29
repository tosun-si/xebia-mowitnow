package com.mowitnow.backend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.mowitnow.backend.AbstractTest;
import com.mowitnow.backend.constant.MowitnowConstant;

import lombok.val;

/**
 * Allows to test treatments of {@link MowerMapper}.
 * 
 * @author Mazlum TOSUN
 */
public class MowerMapperTest extends AbstractTest {

  @Test
  public void givenGoodParams_whenMapToMowers_thenCorrectResultList() {

    // Given.
    val directionsParams = "GAGAGAGAA,AADAADADDA";
    val mowerNumber = directionsParams.split(MowitnowConstant.MOWERS_SEPARATOR).length;
    val positionParams = "12N,33E";

    // When.
    val mowers = MowerMapper.INSTANCE.paramsToMowers(directionsParams, positionParams);

    // Then.
    assertThat(mowers).isNotNull().isNotEmpty().hasSize(mowerNumber);
  }
}
