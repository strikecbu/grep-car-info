package com.andy.grepcarinfo.exception;

public class WrongScrapeCommandException extends RuntimeException {
    public WrongScrapeCommandException() {
    }

    public WrongScrapeCommandException(String message) {
        super(message);
    }

    public WrongScrapeCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
