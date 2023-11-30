package ru.aston.bankaccount.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.bankaccount.entity.Transaction;
import ru.aston.bankaccount.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@Api(value = "контроллер для управления операциями с транзакциями")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation("операция получения всех транзакций")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @ApiOperation("операция получения всех транзакций  по номеру счета")
    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByAccountNumber(@PathVariable UUID accountNumber) {
        List<Transaction> transactions = transactionService.getAllTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}
