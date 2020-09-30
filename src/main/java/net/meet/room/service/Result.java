package net.meet.room.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Result<T> {
    public T value;
    public boolean hasError;
    public String errorString;

    public Result(){}

    public Result(T value) {
        this.value = value;
        hasError = false;
    }

    public Result(T value, boolean error, String errorString) {
        this.value = value;
        this.hasError = error;
        this.errorString = errorString;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
