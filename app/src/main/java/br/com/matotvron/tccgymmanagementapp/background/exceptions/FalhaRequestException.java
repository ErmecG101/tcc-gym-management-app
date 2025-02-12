package br.com.matotvron.tccgymmanagementapp.background.exceptions;

public class FalhaRequestException extends RuntimeException {
    public FalhaRequestException(String message) {
        super(message);
    }
}
