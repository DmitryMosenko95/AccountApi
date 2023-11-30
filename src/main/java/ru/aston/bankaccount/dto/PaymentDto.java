package ru.aston.bankaccount.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    @Min(value = 0, message = "Minimal value should be greater than 0")
    private BigDecimal amount;

    @Pattern(regexp = "^\\d{4}$", message = "Pin code must be 4 digit format")
    private String pin;
}
