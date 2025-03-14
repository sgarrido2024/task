package cl.nisum.controller;

import cl.nisum.UserModelMok;
import cl.nisum.exception.CustomException;
import cl.nisum.exception.ExceptionCodes;
import cl.nisum.model.UserModel;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import cl.nisum.service.UserServiceImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void inicializaMocks() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void saveOK() {
        UserModel userModelMok = new UserModelMok().UserModelMok();
        when(userService.save(userModelMok)).thenReturn(userModelMok);
        ResponseEntity<Object> result = userController.getSave(userModelMok);
        assertEquals(HttpStatus.CREATED,result.getStatusCode());

    }

    @Test
    public void saveError() {
        UserModel userModelMok = new UserModelMok().UserModelMok();
        when(userService.save(userModelMok)).thenThrow(new CustomException(ExceptionCodes.FOUND_EMAIL));
        ResponseEntity<Object> result = userController.getSave(userModelMok);
        assertEquals(HttpStatus.FOUND,result.getStatusCode());

    }

    @Test
    public void updateOK() {
        UserModel userModelMok = new UserModelMok().UserModelMok();
        when(userService.update(userModelMok)).thenReturn(userModelMok);
        ResponseEntity<Object> result = userController.update(userModelMok);
        assertEquals(HttpStatus.ACCEPTED,result.getStatusCode());

    }

    @Test
    public void getFindByIdOK() {
        UserModel userModelMok = new UserModelMok().UserModelMok();
        when(userService.findById("1")).thenReturn(Optional.ofNullable(userModelMok));
        Optional<UserModel> result = userController.getFindById("1");
        assertNotNull(result);

    }
}