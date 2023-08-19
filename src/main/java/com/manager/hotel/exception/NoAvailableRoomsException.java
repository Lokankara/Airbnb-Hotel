package com.manager.hotel.exception;

public class NoAvailableRoomsException
        extends RuntimeException {
    public NoAvailableRoomsException(String message) {
        super(message);
    }
}
