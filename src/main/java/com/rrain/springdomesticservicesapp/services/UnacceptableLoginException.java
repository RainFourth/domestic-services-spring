package com.rrain.springdomesticservicesapp.services;

public class UnacceptableLoginException extends Exception {
    public UnacceptableLoginException(String msg) {
        super(msg);
    }
}
