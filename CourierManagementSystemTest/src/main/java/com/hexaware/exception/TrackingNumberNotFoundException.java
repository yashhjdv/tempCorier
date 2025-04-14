package com.hexaware.exception;

public class TrackingNumberNotFoundException extends Exception {

    public TrackingNumberNotFoundException() {
        super();
    }

    public TrackingNumberNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TrackingNumberNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TrackingNumberNotFoundException(String trackingNumber) {
        super("Tracking Number Not Found: " + trackingNumber);
    }

    public TrackingNumberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
