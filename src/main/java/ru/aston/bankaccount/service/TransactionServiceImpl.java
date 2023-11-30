package ru.aston.bankaccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.bankaccount.entity.Transaction;
import ru.aston.bankaccount.repository.TransactionRepository;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(UUID accountNumber) {
        return transactionRepository.findAllByAccounts(accountNumber.toString());
    }
}


