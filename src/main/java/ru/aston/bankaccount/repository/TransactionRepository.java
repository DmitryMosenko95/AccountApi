package ru.aston.bankaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.aston.bankaccount.entity.Transaction;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(nativeQuery = true, value = "SELECT t.* FROM TRANSACTIONS  t " +
            "JOIN ACCOUNT_TRANSACTION at ON t.id = at.transaction_id " +
            "JOIN ACCOUNTS a ON a.ACCOUNT_NUMBER = at.ACCOUNT_NUMBER " +
            "WHERE a.ACCOUNT_NUMBER = :account")
    List<Transaction> findAllByAccounts(@Param("account") String accountNumber);

}