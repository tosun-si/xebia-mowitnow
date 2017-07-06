package com.mowitnow.backend.service;

/**
 * Service that allows to get application parameters. These parameters allows to build mowers.
 *
 * @author Mazlum TOSUN
 */
public interface IApplicationParamService {

    /**
     * Get directions parameters.
     *
     * @return {@link String} directions parameters
     */
    String getDirections();

    /**
     * Get positions parameters.
     *
     * @return {@link String} positions parameters
     */
    String getPosition();

    /**
     * Get expected result positions parameters.
     *
     * @return {@link String} expected result positions parameters
     */
    String getExpectedPositions();

    /**
     * Get a parameter that represents horizontal X limit of garden.
     *
     * @return {@link String} horizontal X limit of garden
     */
    String getGardenHorizontalLimitMin();

    /**
     * Get a parameter that represents horizontal Y limit of garden.
     *
     * @return {@link String} horizontal Y limit of garden
     */
    String getGardenHorizontalLimitMax();

    /**
     * Get a parameter that represents vertical X limit of garden.
     *
     * @return {@link String} vertical X limit of garden
     */
    String getGardenVerticalLimitMin();

    /**
     * Get a parameter that represents vertical Y limit of garden.
     *
     * @return {@link String} vertical Y limit of garden
     */
    String getGardenVerticalLimitMax();
}
