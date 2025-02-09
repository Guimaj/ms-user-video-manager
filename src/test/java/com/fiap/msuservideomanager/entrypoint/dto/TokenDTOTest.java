package com.fiap.msuservideomanager.entrypoint.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TokenDTOTest {

    @Test
    void deveCriarTokenDTO_Corretamente() {
        TokenDTO tokenDTO = new TokenDTO("jwt-token");

        assertThat(tokenDTO.getJwt()).isEqualTo("jwt-token");
    }

    @Test
    void devePermitirModificarValores() {
        TokenDTO tokenDTO = new TokenDTO("token-inicial");

        tokenDTO.setJwt("novo-token");

        assertThat(tokenDTO.getJwt()).isEqualTo("novo-token");
    }
}
