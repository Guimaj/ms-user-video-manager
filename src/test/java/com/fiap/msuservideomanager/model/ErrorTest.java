package com.fiap.msuservideomanager.model;

import com.fiap.msuservideomanager.domain.model.Error;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorTest {

    @Test
    void deveCriarError_Corretamente() {
        Error error = new Error("2024-02-09T10:00:00Z", 404, "Not Found", "/api/resource");

        assertThat(error.timestamp()).isEqualTo("2024-02-09T10:00:00Z");
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Not Found");
        assertThat(error.path()).isEqualTo("/api/resource");
    }

    @Test
    void deveCriarError_ComValoresNulos() {
        Error error = new Error(null, 0, null, null);

        assertThat(error.timestamp()).isNull();
        assertThat(error.status()).isEqualTo(0);
        assertThat(error.error()).isNull();
        assertThat(error.path()).isNull();
    }
}
