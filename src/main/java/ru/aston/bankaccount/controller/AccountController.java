package ru.aston.bankaccount.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.bankaccount.dto.AccountCreatingRequest;
import ru.aston.bankaccount.dto.PaymentDto;
import ru.aston.bankaccount.entity.Account;
import ru.aston.bankaccount.service.AccountService;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@Api(value = "контроллер для управления операциями с аккаунтами")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation("операция получения всех аккаунтов")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @ApiOperation( "операция получения  аккаунта по номеру счета")
    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable UUID accountNumber) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @ApiOperation("операция сохранения  аккаунта")
    @PostMapping()
    public ResponseEntity<UUID> saveAccount(@RequestBody @Valid AccountCreatingRequest accountCreatingRequest) {
        Account createdAccount = accountService.createAccount(accountCreatingRequest.getName(), accountCreatingRequest.getPin());
        return ResponseEntity.ok(createdAccount.getAccountNumber());
    }

    @ApiOperation("операция добавления суммы на счет")
    @PatchMapping(value = "/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable UUID accountNumber, @RequestBody @Valid PaymentDto paymentDto) {
        accountService.deposit(accountNumber, paymentDto.getAmount());
        return ResponseEntity.ok("Success deposit");
    }

    @ApiOperation("операция перевода от одного аккаунта к другому")
    @PatchMapping(value = "/{fromAccountNumber}/transfer/{toAccountNumber}")
    public ResponseEntity<String> transfer(@PathVariable UUID fromAccountNumber,
                                           @PathVariable UUID toAccountNumber,
                                           @RequestBody @Valid PaymentDto paymentDto) {
        accountService.transfer(fromAccountNumber, toAccountNumber, paymentDto.getAmount(), paymentDto.getPin());
        return ResponseEntity.ok("Success transfer");
    }

    @ApiOperation("операция снятия суммы со счета")
    @PatchMapping(value = "/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable UUID accountNumber, @RequestBody @Valid PaymentDto paymentDto) {
        accountService.withdraw(accountNumber, paymentDto.getAmount(), paymentDto.getPin());
        return ResponseEntity.ok("Success withdraw");
    }
}
