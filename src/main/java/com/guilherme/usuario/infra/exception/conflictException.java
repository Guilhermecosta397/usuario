package com.guilherme.usuario.infra.exception;

public class conflictException extends RuntimeException {
    public conflictException(String message) {
        super(message);
    }

    public conflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
