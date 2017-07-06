package com.mowitnow.backend.service.impl;

import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.exception.ApplicationParamException;
import com.mowitnow.backend.service.IApplicationParamService;
import com.mowitnow.backend.validator.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link IApplicationParamService}.
 *
 * @author Mazlum TOSUN
 * @see IApplicationParamService
 */
@Service
@Data
@Slf4j
public class ApplicationParamServiceImpl implements IApplicationParamService {

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

    /**
     * Allows to initializes application parameters.
     *
     * @throws ApplicationParamException thrown if application parameters are not correctly
     *                                   positioned in application.properties file
     */
    @PostConstruct
    public void init() throws ApplicationParamException {
        LOGGER.debug("Initializing application parameters...");

        // Validates all parameters and gets error messages if it exists validation errors.
        Validator.of(this)
                .validate(ApplicationParamServiceImpl::getPosition, StringUtils::isNotEmpty, "Positions parameters should not be empty")
                .validate(ApplicationParamServiceImpl::getDirections, StringUtils::isNotEmpty, "Directions parameters should not be empty")
                .validate(ApplicationParamServiceImpl::getExpectedPositions, StringUtils::isNotEmpty, "Expected positions parameters should not be empty")
                .validate(Function.identity(), this::positionsLengthSameDirections, "Positions length must be same to directions length")
                .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMin, StringUtils::isNotEmpty, "Garden horizontal limit min should not be empty")
                .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMin, StringUtils::isNumeric, "Garden horizontal limit min should not be in type numeric")
                .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMax, StringUtils::isNotEmpty, "Garden horizontal limit max should not be empty")
                .validate(ApplicationParamServiceImpl::getGardenHorizontalLimitMax, StringUtils::isNumeric, "Garden horizontal limit max should not be in type numeric")
                .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMin, StringUtils::isNotEmpty, "Garden vertical limit min should not be empty")
                .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMin, StringUtils::isNumeric, "Garden vertical limit min should not be in type numeric")
                .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMax, StringUtils::isNotEmpty, "Garden vertical limit max should not be empty")
                .validate(ApplicationParamServiceImpl::getGardenVerticalLimitMax, StringUtils::isNumeric, "Garden vertical limit max should not be in type numeric")
                .get();
    }

    /**
     * Checks if positions length is same to directions length.
     *
     * @param param current service that contains parameters
     * @return boolean for result
     */
    private boolean positionsLengthSameDirections(final ApplicationParamServiceImpl param) {
        return param.getPosition() != null
                && param.getDirections() != null
                && param.getPosition().split(MowitnowConstant.MOWERS_SEPARATOR).length == param.getDirections().split(MowitnowConstant.MOWERS_SEPARATOR).length;
    }
}
