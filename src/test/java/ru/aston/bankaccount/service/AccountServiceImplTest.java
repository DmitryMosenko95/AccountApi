package ru.aston.bankaccount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.aston.bankaccount.entity.Account;
import ru.aston.bankaccount.repository.AccountRepository;
import ru.aston.bankaccount.repository.TransactionRepository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAccount() {
        String name = "TestAccount";
        String pin = "1234";

        Account account = new Account();
        account.setName(name);
        account.setPin(pin);
        account.setAmount(BigDecimal.valueOf(0));

        when(accountRepository.save(any())).thenReturn(account);

        Account createdAccount = accountService.createAccount(name, pin);

        assertNotNull(createdAccount);
        assertEquals(name, createdAccount.getName());
        assertEquals(pin, createdAccount.getPin());
        assertEquals(BigDecimal.valueOf(0), createdAccount.getAmount());

        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(new Account(), new Account()));

        List<Account> accounts = accountService.getAllAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testGetAccountByAccountNumber() {
        UUID accountNumber = UUID.randomUUID();
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccountByAccountNumber(accountNumber);

        assertNotNull(foundAccount);
        assertEquals(accountNumber, foundAccount.getAccountNumber());

        verify(accountRepository, times(1)).findById(accountNumber);
    }

    @Test
    void testDeposit() {
        UUID accountNumber = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAmount(BigDecimal.valueOf(50));

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        accountService.deposit(accountNumber, amount);

        assertEquals(BigDecimal.valueOf(150), account.getAmount());
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void testTransfer() {
        UUID senderAccountNumber = UUID.randomUUID();
        UUID receiverAccountNumber = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);
        String pin = "1234";

        Account senderAccount = new Account();
        senderAccount.setAccountNumber(senderAccountNumber);
        senderAccount.setAmount(BigDecimal.valueOf(200));
        senderAccount.setPin(pin);

        Account receiverAccount = new Account();
        receiverAccount.setAccountNumber(receiverAccountNumber);
        receiverAccount.setAmount(BigDecimal.valueOf(50));
    }
}