package com.mowitnow.function.composition;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;

/**
 * Class that allows to modify some fields on position object.<br>
 * This class allows to add all possible modifications (represented by consumer functions) and each
 * action is associated to an index.<br>
 * When all combination is added, a final method allows to execute the good modifications.
 *
 * @author Mazlum TOSUN
 */
@AllArgsConstructor
public final class PositionModifier {

    private final String positionField;
    private final Map<Integer, Consumer<String>> actions = Maps.newHashMap();

    /**
     * Static factory method that allows to initialize modifier with a field value.
     *
     * @param positionField position field value
     * @return this
     */
    public static PositionModifier from(final String positionField) {
        return new PositionModifier(positionField);
    }

    /**
     * Associates the given action with the given index.
     *
     * @param index  index
     * @param action action
     * @return this
     */
    public PositionModifier modify(final int index, final Consumer<String> action) {
        this.actions.put(index, action);
        return this;
    }

    /**
     * Final method that allows to execute the good action from the given index.<br>
     * This action will modify a field on position with a value (passed on from method).
     *
     * @param index index
     */
    public void execute(final int index) {
        this.actions.getOrDefault(index, p -> {
            throw new IllegalStateException("No action on person was found for index : " + index);
        }).accept(positionField);
    }
}
