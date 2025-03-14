package cl.nisum.service;

import cl.nisum.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application.properties")
public class AuthServiceImplTest {


    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private UserModel userModel;

    private String SECRET_KEY = "9FD24E18EE6C8CC16CD4BB18A119EA4CC813717F843FB37A7078EF432161D01E";

    @Value("${jwt.expiration}")
    private long expirationTime = 86400000L; // 1 day in milliseconds

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateTokenOK() {
        ReflectionTestUtils.setField(
                authServiceImpl,
                "SECRET_KEY",
                "9FD24E18EE6C8CC16CD4BB18A119EA4CC813717F843FB37A7078EF432161D01E");

        when(userModel.getName()).thenReturn("Juan Rodriguez");

        String token = authServiceImpl.generateToken(userModel);

        assertNotNull(token);
    }

    @Test
    public void generateTokenWithValidUser() {
        ReflectionTestUtils.setField(
                authServiceImpl,
                "SECRET_KEY",
                "9FD24E18EE6C8CC16CD4BB18A119EA4CC813717F843FB37A7078EF432161D01E");

        when(userModel.getName()).thenReturn("Juan Rodriguez");

        String token = authServiceImpl.generateToken(userModel);

        assertNotNull(token);
    }


    @Test
    public void generateTokenWithNullUser() {
        ReflectionTestUtils.setField(
                authServiceImpl,
                "SECRET_KEY",
                "9FD24E18EE6C8CC16CD4BB18A119EA4CC813717F843FB37A7078EF432161D01E");

        UserModel nullUser = null;

        assertThrows(NullPointerException.class, () -> {
            authServiceImpl.generateToken(nullUser);
        });
    }
}


