package com.reservationhotel.reservation.web.exceptions;

public class CustomBadRequestException extends RuntimeException {
    private int status;

    public CustomBadRequestException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
