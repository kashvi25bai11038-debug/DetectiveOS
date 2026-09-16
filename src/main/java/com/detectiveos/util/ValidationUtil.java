package com.detectiveos.util;

public final class ValidationUtil {
    private ValidationUtil(){}
    public static String required(String value,String field){
        if(value==null || value.isBlank()) throw new IllegalArgumentException(field+" is required.");
        return value.trim();
    }
    public static int reliability(int value){
        if(value<0 || value>100) throw new IllegalArgumentException("Reliability must be between 0 and 100.");
        return value;
    }
}
