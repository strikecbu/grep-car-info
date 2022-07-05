package com.andy.grepcarinfo.exception;

public class FetchDataException extends RuntimeException {
    private String fetchUrl;

    public FetchDataException(Throwable cause, String fetchUrl) {
        super(cause);
        this.fetchUrl = fetchUrl;
    }

    public String getFetchUrl() {
        return fetchUrl;
    }

    public void setFetchUrl(String fetchUrl) {
        this.fetchUrl = fetchUrl;
    }
}
