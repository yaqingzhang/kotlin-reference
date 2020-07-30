package com.runningmessage.kotref.java.discuss;

/**
 * Created by Lorss on 19-2-15.
 */
public class ApiException extends Exception {

    public int code;
    public String message;
    public String mmm;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}

