package com.mowitnow.backend.dto;

import com.mowitnow.backend.domain.Mower;
import com.mowitnow.backend.domain.Position;

import lombok.Builder;
import lombok.Getter;

/**
 * Object that contains mower final position and mower that concerned by this position.
 * 
 * @author Mazlum TOSUN
 */
@Builder
@Getter
public class PositionFinalDto {

  private final Mower mower;
  private final Position position;
}
