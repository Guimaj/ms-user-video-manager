package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Token;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

    @Test
    void deveCriarToken_Corretamente() {
        Token token = new Token("jwt-token");

        assertThat(token.getJwt()).isEqualTo("jwt-token");
    }

    @Test
    void devePermitirModificarValores() {
        Token token = new Token();

        token.setJwt("novo-token");

        assertThat(token.getJwt()).isEqualTo("novo-token");
    }

    @Test
    void deveCriarToken_Vazio() {
        Token token = new Token();

        assertThat(token).isNotNull();
        assertThat(token.getJwt()).isNull();
    }
}
