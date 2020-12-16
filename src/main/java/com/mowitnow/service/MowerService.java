package com.mowitnow.service;

import com.mowitnow.domain.Mower;
import com.mowitnow.domain.Position;
import com.mowitnow.dto.PositionFinalDto;

import java.util.List;

/**
 * Service that allows to do treatments on {@link Mower}.
 *
 * @author Mazlum TOSUN
 */
public interface MowerService {

    /**
     * Gets final positions of the all mowers that exists in application. Final position contains
     * x/y coordinate and orientation. A result list contains mower final positions and concerned
     * mowers.
     *
     * @return final {@link PositionFinalDto} list
     */
    List<PositionFinalDto> getFinalPositions();

    /**
     * Checks if a mower is in garden surface from its x/y coordinates. That coordinates are given
     * by{@link Position} object. The garden limits are configured in application parameter file.
     *
     * @param position current position that contains x, y coordinate
     * @return boolean for result
     */
    boolean isInGarden(final Position position);
}
