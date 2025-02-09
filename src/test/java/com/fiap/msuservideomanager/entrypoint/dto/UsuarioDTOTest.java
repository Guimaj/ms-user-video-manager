package com.fiap.msuservideomanager.entrypoint.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UsuarioDTOTest {

    @Test
    void deveCriarUsuarioDTO_Corretamente() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("123", "usuario@email.com");

        assertThat(usuarioDTO.getId()).isEqualTo("123");
        assertThat(usuarioDTO.getEmail()).isEqualTo("usuario@email.com");
    }

    @Test
    void devePermitirModificarValores() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId("456");
        usuarioDTO.setEmail("novo@email.com");

        assertThat(usuarioDTO.getId()).isEqualTo("456");
        assertThat(usuarioDTO.getEmail()).isEqualTo("novo@email.com");
    }

    @Test
    void deveCriarUsuarioDTO_Vazio() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        assertThat(usuarioDTO).isNotNull();
        assertThat(usuarioDTO.getId()).isNull();
        assertThat(usuarioDTO.getEmail()).isNull();
    }
}
