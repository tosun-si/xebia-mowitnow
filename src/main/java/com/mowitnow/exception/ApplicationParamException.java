package com.mowitnow.exception;

import lombok.NoArgsConstructor;

/**
 * Exception that thrown when it exists application parameters errors.
 *
 * @author Mazlum TOSUN
 */
@NoArgsConstructor
public class ApplicationParamException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message message
     */
    public ApplicationParamException(String message) {
        super(message);
    }
}
