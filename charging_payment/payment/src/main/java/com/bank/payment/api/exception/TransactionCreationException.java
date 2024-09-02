package com.bank.payment.api.exception;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:38 PM
 */
public class TransactionCreationException extends RuntimeException {

    public TransactionCreationException(String message) {
        super(message);
    }

    public TransactionCreationException(String message,Throwable couse) {
        super(message,couse);
    }
}
