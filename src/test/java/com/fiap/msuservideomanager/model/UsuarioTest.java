package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    void deveCriarUsuario_Corretamente() {
        Usuario usuario = new Usuario("123", "usuario@email.com");

        assertThat(usuario.getId()).isEqualTo("123");
        assertThat(usuario.getEmail()).isEqualTo("usuario@email.com");
    }

    @Test
    void devePermitirModificarValores() {
        Usuario usuario = new Usuario();

        usuario.setId("456");
        usuario.setEmail("novo@email.com");

        assertThat(usuario.getId()).isEqualTo("456");
        assertThat(usuario.getEmail()).isEqualTo("novo@email.com");
    }

    @Test
    void deveCriarUsuario_Vazio() {
        Usuario usuario = new Usuario();

        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isNull();
        assertThat(usuario.getEmail()).isNull();
    }
}
