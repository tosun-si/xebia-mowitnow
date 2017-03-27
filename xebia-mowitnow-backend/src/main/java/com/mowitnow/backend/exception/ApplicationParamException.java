package com.mowitnow.backend.exception;

/**
 * Exception that thrown when it exists application parameters errors.
 * 
 * @author Mazlum TOSUN
 */
public class ApplicationParamException extends Exception {

  private static final long serialVersionUID = -2726531034067868568L;

  /**
   * Constructor without parameters.
   */
  public ApplicationParamException() {
    super();
  }

  /**
   * Constructor with message.
   * 
   * @param message message
   */
  public ApplicationParamException(String message) {
    super(message);
  }
}
