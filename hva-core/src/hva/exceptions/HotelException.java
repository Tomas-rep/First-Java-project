package hva.exceptions;

import java.io.Serial;

/**
 * Basic exception class for Hotel operations.
 */
public abstract class HotelException extends Exception {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    public HotelException() {
        super("(empty message)");
    }

    /** 
     * @param message
     */
    public HotelException(String message) {
        super(message);
    }

}