package com.fiap.msuservideomanager.entrypoint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Email(message = "Email inválido!")
    private String email;
    @NotEmpty
    private String senha;
}
