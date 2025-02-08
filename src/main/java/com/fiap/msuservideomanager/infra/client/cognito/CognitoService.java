package com.fiap.msuservideomanager.infra.client.cognito;

import com.fiap.msuservideomanager.application.port.LoginPort;
import com.fiap.msuservideomanager.domain.exception.UsuarioExistenteException;
import com.fiap.msuservideomanager.domain.exception.UsuarioOuSenhaInvalidoException;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import com.fiap.msuservideomanager.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class CognitoService implements LoginPort {

    @Value("${aws.cognito.secret}")
    private String secret;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoService(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @Override
    public Usuario cadastrarUsuario(Login login) {
        try {
            AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(login.getEmail())
                    .temporaryPassword("Temp@1234")
                    .userAttributes(List.of(
                            AttributeType.builder().name("email").value(login.getEmail()).build(),
                            AttributeType.builder().name("email_verified").value("true").build()
                    ))
                    .messageAction("SUPPRESS")
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(request);

            atualizarSenhaPermanente(login.getEmail(), login.getSenha());

            String sub = response.user()
                    .attributes()
                    .stream()
                    .filter(attr -> attr.name().equals("sub"))
                    .findFirst()
                    .map(AttributeType::value)
                    .orElse("");

            return new Usuario(sub, response.user().username());
        }
        catch (UsernameExistsException exception) {
            throw new UsuarioExistenteException();
        }
    }

    @Override
    public Token autenticarUsuario(Login login) {
        try {
            String secretHash = calcularSecretHash(clientId, secret, login.getEmail());

            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .clientId(clientId)
                    .authParameters(
                            Map.of(
                                    "USERNAME", login.getEmail(),
                                    "PASSWORD", login.getSenha(),
                                    "SECRET_HASH", secretHash
                            )
                    )
                    .build();

            InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);

            return new Token(authResponse.authenticationResult().idToken());
        }
        catch (NotAuthorizedException exception) {
            throw new UsuarioOuSenhaInvalidoException();
        }
    }

    private void atualizarSenhaPermanente(String email, String novaSenha) {
        try {
            String secretHash = calcularSecretHash(clientId, secret, email);

            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .clientId(clientId)
                    .authParameters(Map.of(
                            "USERNAME", email,
                            "PASSWORD", "Temp@1234",
                            "SECRET_HASH", secretHash
                    ))
                    .build();

            InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);


            if (authResponse.challengeName() == ChallengeNameType.NEW_PASSWORD_REQUIRED) {
                String session = authResponse.session();

                RespondToAuthChallengeRequest challengeRequest = RespondToAuthChallengeRequest.builder()
                        .clientId(clientId)
                        .challengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                        .challengeResponses(Map.of(
                                "USERNAME", email,
                                "NEW_PASSWORD", novaSenha,
                                "SECRET_HASH", secretHash
                        ))
                        .session(session)
                        .build();

                cognitoClient.respondToAuthChallenge(challengeRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir senha permanente: " + e.getMessage(), e);
        }
    }

    private String calcularSecretHash(String clientId, String clientSecret, String username) {
        try {
            String message = username + clientId;
            SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular o Client Secret Hash", e);
        }
    }
}
