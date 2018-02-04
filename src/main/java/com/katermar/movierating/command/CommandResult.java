package com.katermar.movierating.command;

/**
 * Created by katermar on 12/31/2017.
 * <p>
 * Class used to hold the path to go and transfer method: forward or redirect.
 * Also it can hold error code and error message, if response type is ERROR.
 */
public class CommandResult {
    private ResponseType responseType;
    private String page;
    private int errorCode;
    private String errorMessage;

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
    }

    public CommandResult(ResponseType responseType, int errorCode, String errorMessage) {
        this.responseType = responseType;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public enum ResponseType {
        FORWARD, REDIRECT, ERROR
    }
}
