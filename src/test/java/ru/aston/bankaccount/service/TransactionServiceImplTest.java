package ru.aston.bankaccount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.aston.bankaccount.entity.Transaction;
import ru.aston.bankaccount.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setTime(LocalTime.now());

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setTime(LocalTime.now());

        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();

        assertEquals(transactions.size(), result.size());
    }

    @Test
    void testGetAllTransactionsByAccountNumber() {
        UUID accountNumber = UUID.randomUUID();
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setTime(LocalTime.now());

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setTime(LocalTime.now());

        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAllByAccounts(anyString())).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactionsByAccountNumber(accountNumber);

        assertEquals(transactions.size(), result.size());
    }
}