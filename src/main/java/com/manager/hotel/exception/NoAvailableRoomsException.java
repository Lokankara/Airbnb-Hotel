package com.manager.hotel.exception;

public class NoAvailableRoomsException
        extends RuntimeException {
    public NoAvailableRoomsException(
            final String message) {
        super(message);
    }
}
