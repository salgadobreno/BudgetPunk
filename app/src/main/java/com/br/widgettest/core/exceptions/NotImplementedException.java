package com.br.widgettest.core.exceptions;

/**
 * Created by Breno on 1/17/2016.
 */
public class NotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "not implemented";
    }
}
