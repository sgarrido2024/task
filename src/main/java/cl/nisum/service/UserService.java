package cl.nisum.service;

import cl.nisum.model.UserModel;

import java.util.Optional;

public interface UserService {

    Object save(UserModel userModel);

    UserModel update(UserModel userModel);

    Optional<UserModel> findById(String id);



}
