package br.com.matotvron.tccgymmanagementapp.background.exceptions;

public class FalhaRequestException extends RuntimeException {
    final private int responseCode;
    public FalhaRequestException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
