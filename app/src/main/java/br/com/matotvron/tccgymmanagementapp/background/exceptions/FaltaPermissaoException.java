package br.com.matotvron.tccgymmanagementapp.background.exceptions;

public class FaltaPermissaoException extends RuntimeException {
    public FaltaPermissaoException(String message) {
        super(message);
    }
}
