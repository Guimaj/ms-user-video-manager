package com.fiap.msuservideomanager.entrypoint.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

class LoginDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarLoginDTO_Corretamente() {
        LoginDTO loginDTO = new LoginDTO("usuario@email.com", "senha123");

        assertThat(loginDTO.getEmail()).isEqualTo("usuario@email.com");
        assertThat(loginDTO.getSenha()).isEqualTo("senha123");
    }

    @Test
    void devePermitirModificarValores() {
        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail("novo@email.com");
        loginDTO.setSenha("novaSenha");

        assertThat(loginDTO.getEmail()).isEqualTo("novo@email.com");
        assertThat(loginDTO.getSenha()).isEqualTo("novaSenha");
    }

    @Test
    void deveCriarLoginDTO_Vazio() {
        LoginDTO loginDTO = new LoginDTO();

        assertThat(loginDTO).isNotNull();
        assertThat(loginDTO.getEmail()).isNull();
        assertThat(loginDTO.getSenha()).isNull();
    }

    @Test
    void deveFalhar_QuandoEmailInvalido() {
        LoginDTO loginDTO = new LoginDTO("email-invalido", "senha123");

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email inválido!");
    }

    @Test
    void deveFalhar_QuandoSenhaVazia() {
        LoginDTO loginDTO = new LoginDTO("usuario@email.com", "");

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO);

        assertThat(violations).isNotEmpty();
    }
}
