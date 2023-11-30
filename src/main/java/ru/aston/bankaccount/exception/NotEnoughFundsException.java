package ru.aston.bankaccount.exception;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException() {
        super("Not Enough Funds");
    }
}