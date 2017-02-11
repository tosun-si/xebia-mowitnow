package com.mowitnow.backend.mapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mowitnow.backend.constant.MowitnowConstant;
import com.mowitnow.backend.domain.Position;
import com.mowitnow.backend.dto.PositionFinalDto;
import com.mowitnow.backend.function.composition.PositionModifier;

/**
 * Mapper that allows to transform object that concerns {@link PositionFinalDto}.
 * 
 * @author Mazlum TOSUN
 */
public enum PositionMapper {

  // Single instance.
  INSTANCE;

  // ----------------------------------------------
  // Public methods
  // ----------------------------------------------

  /**
   * Allows to transform the given positions parameters to {@link Position} list.
   * 
   * @param positionParams position parameters
   * @return {@link Position} list
   */
  public List<Position> paramsToPositions(final String positionParams) {

    // Transforms the given position parameters to position objects.
    return Pattern.compile(MowitnowConstant.MOWERS_SEPARATOR).splitAsStream(positionParams)
        .map(this::toPositions).collect(Collectors.toList());
  }

  // ----------------------------------------------
  // Private methods
  // ----------------------------------------------

  /**
   * Allows to transform the given position string (parameter) to {@link Position}.
   * 
   * @param positionParam current positions
   * @return {@link Position} position
   */
  private Position toPositions(final String positionParam) {

    // Initializes an atomic index.
    final AtomicInteger index = new AtomicInteger(0);

    // Initializes position to create.
    final Position position = new Position();

    // Iterates overs all characters of current position. First character corresponds to X
    // coordinate, the second corresponds to Y coordinate and the last corresponds to orientation.
    // Consumers based on character index, allow to call the appropriate setter of person to create.
    positionParam.chars().mapToObj(i -> (char) i).map(String::valueOf)
        .forEach(field -> modifyPositionField(index.incrementAndGet(), field, position));

    // Returns created position.
    return position;
  }

  /**
   * Allows to modify a field of the given {@link Position}. The goal is to associate an action to
   * an index. According to the index, a good setter of the given {@link Position}, will be
   * called.<br>
   * First character corresponds to X coordinate, the second corresponds to Y coordinate and the
   * last corresponds to orientation.<br>
   * 
   * @param position current position
   */
  private void modifyPositionField(final int index, final String positionField,
      final Position position) {

    PositionModifier.from(positionField).modify(1, position::coordinateX)
        .modify(2, position::coordinateY).modify(3, position::orientation).execute(index);
  }
}
