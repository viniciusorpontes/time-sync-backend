package br.com.timesync.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String mensagem) {
        super(mensagem);
    }

}