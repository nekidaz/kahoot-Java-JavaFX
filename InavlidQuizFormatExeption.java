package com.example.p2;

public class InavlidQuizFormatExeption extends Exception {
    public InavlidQuizFormatExeption() {
    }

    public InavlidQuizFormatExeption(String message) {
        super(message);
    }

    public InavlidQuizFormatExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public InavlidQuizFormatExeption(Throwable cause) {
        super(cause);
    }

    public InavlidQuizFormatExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

