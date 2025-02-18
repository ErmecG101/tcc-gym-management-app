package br.com.matotvron.tccgymmanagementapp.background.exceptions;

public class FalhaServidorException extends RuntimeException {

    final private int responseCode;
    public FalhaServidorException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
