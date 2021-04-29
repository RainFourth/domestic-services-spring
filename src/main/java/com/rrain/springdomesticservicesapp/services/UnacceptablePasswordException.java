package com.rrain.springdomesticservicesapp.services;

public class UnacceptablePasswordException extends Exception {
    public UnacceptablePasswordException(String msg) {
        super(msg);
    }
}
