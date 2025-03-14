package cl.nisum.service;

import cl.nisum.model.UserModel;

public interface AuthService {

    String generateToken(UserModel userModel);

}
