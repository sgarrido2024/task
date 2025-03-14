package cl.nisum.controller;


import cl.nisum.exception.CustomException;
import cl.nisum.model.UserModel;
import cl.nisum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController() {
    }


    @PostMapping("/add")
    public ResponseEntity<Object> getSave(@RequestBody UserModel userModel) throws CustomException {
        try {

            Object responseEntity = userService.save(userModel);
            return new ResponseEntity<>(responseEntity,null,HttpStatus.CREATED);
        }catch (CustomException ex){
            return new ResponseEntity<>(ex.getReason(),null,ex.getRawStatusCode());

        }

    }

    @PostMapping("/update")
    public ResponseEntity<Object>  update(@RequestBody UserModel userModel){
        Object responseEntity = userService.update(userModel);
        return new ResponseEntity<>(responseEntity,null,HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{id}")
    public Optional<UserModel> getFindById(@PathVariable String id){
        return userService.findById(id);
    }

}
