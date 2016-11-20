package com.mowitnow.backend.service.impl;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.exception.ApplicationParamException;
import com.mowitnow.backend.service.IApplicationParamService;
import com.mowitnow.backend.validator.Validator;

/**
 * Implementation of {@link IApplicationParamService}.
 * 
 * @see IApplicationParamService
 * @author Mazlum TOSUN
 */
@Service
public class ApplicationParamServiceImpl implements IApplicationParamService {

  /** Logger. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationParamServiceImpl.class);

  // ----------------------------------------------
  // Fields
  // ----------------------------------------------

  @Value("${mower.positions}")
  private String position;
  @Value("${mower.directions}")
  private String directions;
  @Value("${mower.expectedPositions}")
  private String expectedPositions;

  @Value("${garden.limit.horizontal.min}")
  private String gardenHorizontalLimitMin;
  @Value("${garden.limit.horizontal.max}")
  private String gardenHorizontalLimitMax;
  @Value("${garden.limit.vertical.min}")
  private String gardenVerticalLimitMin;
  @Value("${garden.limit.vertical.max}")
  private String gardenVerticalLimitMax;

  // ----------------------------------------------
  // Init
  // ----------------------------------------------

  /**
   * Allows to initializes application parameters.
   * 
   * @throws ApplicationParamException thrown if application parameters are not correctly positioned
   *         in application.properties file
   */
  @PostConstruct
  public void init() throws ApplicationParamException {
    LOGGER.debug("Initializing application parameters...");

    // Validates all parameters and gets error messages if it exists validation errors.
    Validator.of(this)
        .validate(ApplicationParamServiceImpl::getPosition, StringUtils::isNotEmpty,
            "Positions parameters should not be empty")
        .validate(ApplicationParamServiceImpl::getDirections, StringUtils::isNotEmpty,
            "Directions parameters should not be empty")
        .validate(ApplicationParamServiceImpl::getExpectedPositions, StringUtils::isNotEmpty,
            "Expected positions parameters should not be empty")
        .validate(Function.identity(), this::positionsLengthSameDirections,
            "Positions length must be same to directions length")
        .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMin, StringUtils::isNotEmpty,
            "Garden horizontal limit min should not be empty")
        .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMin, StringUtils::isNumeric,
            "Garden horizontal limit min should not be in type numeric")
        .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMax, StringUtils::isNotEmpty,
            "Garden horizontal limit max should not be empty")
        .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMax, StringUtils::isNumeric,
            "Garden horizontal limit max should not be in type numeric")
        .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMin, StringUtils::isNotEmpty,
            "Garden vertical limit min should not be empty")
        .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMin, StringUtils::isNumeric,
            "Garden vertical limit min should not be in type numeric")
        .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMax, StringUtils::isNotEmpty,
            "Garden vertical limit max should not be empty")
        .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMax, StringUtils::isNumeric,
            "Garden vertical limit max should not be in type numeric")
        .get();
  }

  // ----------------------------------------------
  // Private methods
  // ----------------------------------------------

  /**
   * Checks if positions length is same to directions length.
   * 
   * @param param current service that contains parameters
   * @return boolean for result
   */
  private boolean positionsLengthSameDirections(final ApplicationParamServiceImpl param) {
    return param.getPosition() != null && param.getDirections() != null
        && param.getPosition().split(MowitnowConstant.MOWERS_SEPARATOR).length == param
            .getDirections().split(MowitnowConstant.MOWERS_SEPARATOR).length;
  }

  // ----------------------------------------------
  // Getters/setters
  // ----------------------------------------------

  @Override
  public String getDirections() {
    return this.directions;
  }

  public void setDirections(String directions) {
    this.directions = directions;
  }

  @Override
  public String getPosition() {
    return this.position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String getExpectedPositions() {
    return this.expectedPositions;
  }

  public void setExpectedPositions(String expectedPositions) {
    this.expectedPositions = expectedPositions;
  }

  public void setGardenHorizontalLimitMin(String gardenHorizontalLimitMin) {
    this.gardenHorizontalLimitMin = gardenHorizontalLimitMin;
  }

  public void setGardenHorizontalLimitMax(String gardenHorizontalLimitMax) {
    this.gardenHorizontalLimitMax = gardenHorizontalLimitMax;
  }

  public void setGardenVerticalLimitMin(String gardenVerticalLimitMin) {
    this.gardenVerticalLimitMin = gardenVerticalLimitMin;
  }

  public void setGardenVerticalLimitMax(String gardenVerticalLimitMax) {
    this.gardenVerticalLimitMax = gardenVerticalLimitMax;
  }

  @Override
  public String getGardenHorizontalLimitMin() {
    return gardenHorizontalLimitMin;
  }

  @Override
  public String getGardenHorizontalLimitMax() {
    return gardenHorizontalLimitMax;
  }

  @Override
  public String getGardenVerticalLimitMin() {
    return gardenVerticalLimitMin;
  }

  @Override
  public String getGardenVerticalLimitMax() {
    return gardenVerticalLimitMax;
  }
}
