package br.com.matotvron.tccgymmanagementapp.background.tasks;

public enum TaskResults{
    SUCCESS,
    SERVER_ERROR,
    REQUEST_ERROR,
    MISSING_PERMISSIONS,
    IOEXCEPTION,
    UNKNOWN_ERROR,
    WRONG_CREDENTIALS,
    NO_INTERNET,
    FAILED_TO_CONNECT,
    USER_NOT_FOUND,
    GYM_NOT_FOUND,
}
