package com.yujian.entity;

public enum UserRole {
    FITNESSROOM("1"),
    COACH("2"),
    USER("3"),
    ATTENTION ("4"),
    FANS("5"),
    RECOMMEND("6");
    UserRole(String i) {
        value = i;
    }
    private final String value;
    @Override
    public String toString() {
        return value;
    }
}
