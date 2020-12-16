package com.mowitnow.mapper;

import com.mowitnow.AbstractTest;
import com.mowitnow.constant.MowitnowConstant;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import lombok.val;

import static org.assertj.core.api.Assertions.assertThat;

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
        val mowers = MowerMapper.paramsToMowers(directionsParams, positionParams);

        // Then.
        Assertions.assertThat(mowers).isNotNull().isNotEmpty().hasSize(mowerNumber);
    }
}
