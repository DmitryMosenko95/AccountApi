package ru.aston.bankaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.bankaccount.entity.Account;


import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
