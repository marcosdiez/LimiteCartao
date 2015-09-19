package com.marcosdiez.extratocartao;

/**
 * Created by Marcos on 2015-09-19.
 */
public class ParsingSmsException extends Exception {
    public String msg;
    public Exception innerException;
    public ParsingSmsException(String msg, Exception innerException){
        this.msg = msg;
        this.innerException = innerException;
    }
}
