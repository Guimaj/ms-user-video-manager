package com.fiap.msuservideomanager.usecase;

import com.fiap.msuservideomanager.application.usecase.ExtrairUsuarioTokenUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtrairUsuarioTokenUseCaseTest {

    @InjectMocks
    private ExtrairUsuarioTokenUseCase extrairUsuarioTokenUseCase;

    private Authentication authentication;
    private SecurityContext securityContext;
    private Jwt jwt;

    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        jwt = mock(Jwt.class);
    }

    @Test
    void testExtrairClaimUsuarioToken_DeveRetornarClaim() {
        when(jwt.getClaim("email")).thenReturn("user@example.com");

        when(authentication.getPrincipal()).thenReturn(jwt);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            String resultado = extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("email");

            assertNotNull(resultado);
            assertEquals("user@example.com", resultado);
        }
    }

    @Test
    void testExtrairClaimUsuarioToken_QuandoAutenticacaoNula_DeveRetornarNull() {
        when(securityContext.getAuthentication()).thenReturn(null);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            String resultado = extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("email");

            assertNull(resultado);
        }
    }

    @Test
    void testExtrairClaimUsuarioToken_QuandoPrincipalNaoForJwt_DeveRetornarNull() {
        when(authentication.getPrincipal()).thenReturn("InvalidPrincipal");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            String resultado = extrairUsuarioTokenUseCase.extrairClaimUsuarioToken("email");

            assertNull(resultado);
        }
    }
}
