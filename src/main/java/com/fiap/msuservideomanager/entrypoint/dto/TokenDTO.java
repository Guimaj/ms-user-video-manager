package com.fiap.msuservideomanager.entrypoint.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotEmpty
public class TokenDTO {
    private String jwt;
}
