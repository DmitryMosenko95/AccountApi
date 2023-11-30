package ru.aston.bankaccount.service;


import ru.aston.bankaccount.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getAllTransactionsByAccountNumber(UUID accountNumber);
}
