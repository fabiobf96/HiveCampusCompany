package it.hivecampuscompany.hivecampus.logic.exception;

public class PasswordMismatchException extends Exception{
    public PasswordMismatchException() {
        super("La password e la conferma password non coincidono.");
    }
}
