package br.com.adopet.domain.model.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException() {
        super("Email already registered.");
    }
}
