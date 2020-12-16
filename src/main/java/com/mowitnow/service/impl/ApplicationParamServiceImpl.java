package com.mowitnow.service.impl;

import com.mowitnow.constant.MowitnowConstant;
import com.mowitnow.exception.ApplicationParamException;
import com.mowitnow.service.ApplicationParamService;
import com.mowitnow.validator.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link ApplicationParamService}.
 *
 * @author Mazlum TOSUN
 * @see ApplicationParamService
 */
@Service
@Data
@Slf4j
public class ApplicationParamServiceImpl implements ApplicationParamService {

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
        final Supplier<Integer> positionLength = () -> param.getPosition()
                .split(MowitnowConstant.MOWERS_SEPARATOR)
                .length;

        final Supplier<Integer> directionLength = () -> param.getDirections()
                .split(MowitnowConstant.MOWERS_SEPARATOR)
                .length;

        return param.getPosition() != null
                && param.getDirections() != null
                && positionLength.get().equals(directionLength.get());
    }
}
