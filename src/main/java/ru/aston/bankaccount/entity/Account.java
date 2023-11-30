package ru.aston.bankaccount.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {


    @Id
    @UuidGenerator
    private UUID accountNumber;

    private String name;

    @Column(length = 4)
    private String pin;

    @Column(columnDefinition = "numeric")
    @ColumnDefault(value = "0")
    private BigDecimal amount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "accountNumber"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private Set<Transaction> transactions;

}
