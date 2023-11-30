package ru.aston.bankaccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aston.bankaccount.entity.Account;
import ru.aston.bankaccount.entity.Transaction;
import ru.aston.bankaccount.entity.TransactionType;
import ru.aston.bankaccount.exception.InvalidPinException;
import ru.aston.bankaccount.exception.NotEnoughFundsException;
import ru.aston.bankaccount.exception.NotFoundException;
import ru.aston.bankaccount.repository.AccountRepository;
import ru.aston.bankaccount.repository.TransactionRepository;


import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account createAccount(String name, String pin) {
        Account account = new Account();
        account.setName(name);
        account.setPin(pin);
        account.setAmount(BigDecimal.ZERO);
        account.setTransactions(new HashSet<>());
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountByAccountNumber(UUID accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    @Transactional
    public void deposit(UUID accountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account with id not found"));

        initializeTransactions(account);

        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .amount(amount)
                .transactionType(TransactionType.DEPOSIT)
                .accounts(new HashSet<>())
                .build();

        transaction.getAccounts().add(account);
        account.setAmount(account.getAmount().add(amount));
        account.getTransactions().add(transaction);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(UUID fromAccountNumber, UUID toAccountNumber, BigDecimal amount, String pin) {
        Account sender = accountRepository.findById(fromAccountNumber)
                .orElseThrow(() -> new NotFoundException("Sender account not found"));

        Account receiver = accountRepository.findById(toAccountNumber)
                .orElseThrow(() -> new NotFoundException("Receiver account not found"));

        if (!sender.getPin().equals(pin)) {
            throw new InvalidPinException("Pin is not verified");
        }
        if (sender.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }

        initializeTransactions(sender);
        initializeTransactions(receiver);

        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .accounts(new HashSet<>())
                .amount(amount)
                .info("Transfer from " + sender.getAccountNumber() + " to " + receiver.getAccountNumber())
                .transactionType(TransactionType.TRANSFER).build();

        transaction.getAccounts().add(receiver);
        transaction.getAccounts().add(sender);

        sender.setAmount(sender.getAmount().subtract(amount));
        receiver.setAmount(receiver.getAmount().add(amount));

        sender.getTransactions().add(transaction);
        receiver.getTransactions().add(transaction);

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void withdraw(UUID accountNumber, BigDecimal amount, String pin) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account with id not found"));

        if (account.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }
        if (!account.getPin().equals(pin)) {
            throw new InvalidPinException("Pin is not verified");
        }

        initializeTransactions(account);

        Transaction transaction = Transaction.builder()
                .time(LocalTime.now())
                .amount(amount)
                .accounts(new HashSet<>())
                .transactionType(TransactionType.WITHDRAW).build();
        transaction.getAccounts().add(account);

        account.setAmount(account.getAmount().subtract(amount));
        account.getTransactions().add(transaction);

        transactionRepository.save(transaction);
    }


    private void initializeTransactions(Account account) {
        if (account.getTransactions() == null) {
            account.setTransactions(new HashSet<>());
        }
    }
}
