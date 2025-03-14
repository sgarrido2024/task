package cl.nisum.service;

import cl.nisum.dto.UserResponse;
import cl.nisum.exception.CustomException;
import cl.nisum.exception.ExceptionCodes;
import cl.nisum.repository.UserJpaRepository;
import cl.nisum.model.UserModel;
import cl.nisum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AuthService authService;

    @Override
    public Object save(UserModel userModel) throws CustomException{
        UserResponse userRep = null;
        try {
            Boolean boolPw  = validPassword(userModel.getPassword());
            UserModel response = validUser(userModel.getEmail());
            UserModel user = new UserModel();
            Timestamp today = new Timestamp(System.currentTimeMillis());
            user.setName(userModel.getName());
            user.setEmail(userModel.getEmail());
            user.setPassword(userModel.getPassword());
            user.setCreated(today);
            user.setModified(today);
            user.setLastLogin(today);
            user.setActive(true);
            user.setPhoneModel(userModel.getPhoneModel());
            UserModel repUserModel =  userJpaRepository.save(user);
            String token = authService.generateToken(user);
            userRep =  mapResponse(repUserModel,token);
        } catch (CustomException exception) {
            throw exception;
        }
            return userRep;
    }

    @Override
    public UserModel update(UserModel userModel) {
        UserModel user = new UserModel();
        Timestamp today = new Timestamp(System.currentTimeMillis());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setModified(today);
        user.setLastLogin(today);
        user.setActive(true);
        user.setPhoneModel(userModel.getPhoneModel());
        return userJpaRepository.saveAndFlush(user);
    }

    @Override
    public Optional<UserModel> findById(String id) {
        return  userJpaRepository.findById(Long.parseLong(id));
    }


    private UserModel findByEmail(String email) {
        UserModel userModel = userJpaRepository.findByEmail(email);
        return  userModel;
    }
    private boolean validPassword(String password){
        if (!Util.validPassword(password) ){
            throw new CustomException(ExceptionCodes.ERROR_PASSWORD);
        }
        return true;
    }
    private UserModel validUser(String email) throws CustomException{

        if (!Util.validaEmail(email) ){
            throw new CustomException(ExceptionCodes.ERROR_PASSWORD);
        }


        UserModel userM = findByEmail(email);
        if (userM!=null){
            throw new CustomException(ExceptionCodes.FOUND_EMAIL);
        }
        return userM;
    }

    UserResponse mapResponse(UserModel userModel, String token) {

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userModel.getId());
        userResponse.setCreated(userModel.getCreated());
        userResponse.setModified(userModel.getModified());
        userResponse.setLastLogin(userModel.getLastLogin());
        userResponse.setToken(token);
        userResponse.setActive(userModel.isActive());
        return  userResponse;


    }
}
