package ru.aston.bankaccount.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatingRequest {
    @NotNull(message = "username must not be empty")
    private String name;

    @Pattern(regexp = "^\\d{4}$", message = "Pin code must be a 4-digit format")
    private String pin;
}
