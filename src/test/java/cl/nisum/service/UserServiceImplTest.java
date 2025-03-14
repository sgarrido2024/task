package cl.nisum.service;


import cl.nisum.dto.UserResponse;
import cl.nisum.exception.CustomException;
import cl.nisum.model.PhoneModel;
import cl.nisum.model.UserModel;
import cl.nisum.repository.UserJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTc0MTk3ODM3MCwiZXhwIjoxNzQxOTgxOTcwfQ.1iYCwbNYY--poGmEJ7n1ppL8PwEMIX73zUmjC0IwrXo";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveWithValidUserModel() throws CustomException {
        UserModel userModel = new UserModel();
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setId(1L);
        phoneModel.setNumber("1234567");
        phoneModel.setCitycode("1");
        phoneModel.setContrycode("57");

        List<PhoneModel> lstphoneModel = new ArrayList();
        lstphoneModel.add(phoneModel);
        userModel.setName("Test User");
        userModel.setEmail("test@example.com");
        userModel.setPassword("Password123@");
        userModel.setPhoneModel(lstphoneModel);

        UserModel savedUserModel = new UserModel();
        savedUserModel.setId(1L);
        savedUserModel.setName("Test User");
        savedUserModel.setEmail("test@example.com");
        savedUserModel.setPassword("Password123");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        savedUserModel.setCreated(timestamp);
        savedUserModel.setModified(timestamp);
        savedUserModel.setLastLogin(timestamp);
        savedUserModel.setActive(true);
        savedUserModel.setPhoneModel(lstphoneModel);

        when(userJpaRepository.save(any(UserModel.class))).thenReturn(savedUserModel);
        when(authService.generateToken(any(UserModel.class))).thenReturn(token);

        UserResponse response = (UserResponse) userServiceImpl.save(userModel);

        assertNotNull(response);

        assertEquals(savedUserModel.getCreated(), response.getCreated());
        assertEquals(savedUserModel.getModified(), response.getModified());
        assertEquals(savedUserModel.getLastLogin(), response.getLastLogin());
        assertEquals(token, response.getToken());
        assertTrue(response.isActive());
    }

    @Test
    public void saveWithInvalidPassword() {
        UserModel userModel = new UserModel();
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setId(1L);
        phoneModel.setNumber("1234567");
        phoneModel.setCitycode("1");
        phoneModel.setContrycode("57");
        List<PhoneModel> lstphoneModel = new ArrayList();
        lstphoneModel.add(phoneModel);
        userModel.setName("Test User");
        userModel.setEmail("test@example.com");
        userModel.setPassword("invalid");
        userModel.setPhoneModel(lstphoneModel);

        assertThrows(CustomException.class, () -> {
            userServiceImpl.save(userModel);
        });
    }

    @Test
    public void saveWithExistingEmail() {
        UserModel userModel = new UserModel();
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setId(1L);
        phoneModel.setNumber("1234567");
        phoneModel.setCitycode("1");
        phoneModel.setContrycode("57");
        List<PhoneModel> lstphoneModel = new ArrayList();
        userModel.setName("Test User");
        userModel.setEmail("existing@example.com");
        userModel.setPassword("Password123");
        userModel.setPhoneModel(lstphoneModel);

        when(userJpaRepository.findByEmail(userModel.getEmail())).thenReturn(userModel);

        assertThrows(CustomException.class, () -> {
            userServiceImpl.save(userModel);
        });
    }

    @Test
    public void updateWithValidUserModel() {
        UserModel userModel = new UserModel();
        PhoneModel phoneModel = new PhoneModel();
        phoneModel.setId(1L);
        phoneModel.setNumber("1234567");
        phoneModel.setCitycode("1");
        phoneModel.setContrycode("57");
        List<PhoneModel> lstphoneModel = new ArrayList();
        userModel.setName("Updated User");
        userModel.setEmail("updated@example.com");
        userModel.setPassword("UpdatedPassword123");
        userModel.setPhoneModel(lstphoneModel);

        UserModel updatedUserModel = new UserModel();
        updatedUserModel.setId(1L);
        updatedUserModel.setName("Updated User");
        updatedUserModel.setEmail("updated@example.com");
        updatedUserModel.setPassword("UpdatedPassword123");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        updatedUserModel.setModified(timestamp);
        updatedUserModel.setLastLogin(timestamp);
        updatedUserModel.setActive(true);
        updatedUserModel.setPhoneModel(lstphoneModel);

        when(userJpaRepository.saveAndFlush(any(UserModel.class))).thenReturn(updatedUserModel);

        UserModel response = userServiceImpl.update(userModel);

        assertNotNull(response);
        assertEquals(updatedUserModel.getId(), response.getId());
        assertEquals(updatedUserModel.getName(), response.getName());
        assertEquals(updatedUserModel.getEmail(), response.getEmail());
        assertEquals(updatedUserModel.getPassword(), response.getPassword());
        assertEquals(updatedUserModel.getModified(), response.getModified());
        assertEquals(updatedUserModel.getLastLogin(), response.getLastLogin());
        assertTrue(response.isActive());
    }


}
