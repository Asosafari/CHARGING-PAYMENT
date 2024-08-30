package com.company.charging.api.exception;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:52 PM
 */
public class TransactionCreationException extends RuntimeException {

    public TransactionCreationException(String message) {
        super(message);
    }

    public TransactionCreationException(String message,Throwable couse) {
        super(message,couse);
    }
}
