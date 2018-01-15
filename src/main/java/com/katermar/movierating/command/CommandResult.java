package com.katermar.movierating.command;

/**
 * Created by katermar on 12/31/2017.
 */
public class CommandResult {
    private ResponseType responseType;
    private String page;

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
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

    public enum ResponseType {
        FORWARD, REDIRECT, ERROR
    }
}
