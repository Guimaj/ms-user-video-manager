package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Login;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LoginTest {

    @Test
    void deveCriarLogin_Corretamente() {
        Login login = new Login("usuario@email.com", "senha123");

        assertThat(login.getEmail()).isEqualTo("usuario@email.com");
        assertThat(login.getSenha()).isEqualTo("senha123");
    }

    @Test
    void devePermitirModificarValores() {
        Login login = new Login();

        login.setEmail("novo@email.com");
        login.setSenha("novaSenha");

        assertThat(login.getEmail()).isEqualTo("novo@email.com");
        assertThat(login.getSenha()).isEqualTo("novaSenha");
    }

    @Test
    void deveCriarLogin_Vazio() {
        Login login = new Login();

        assertThat(login).isNotNull();
        assertThat(login.getEmail()).isNull();
        assertThat(login.getSenha()).isNull();
    }
}
