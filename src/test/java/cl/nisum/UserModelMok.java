package cl.nisum;

import cl.nisum.model.PhoneModel;
import cl.nisum.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModelMok {

    public UserModel UserModelMok(){
        PhoneModel phoneModel = new PhoneModel();
        List<PhoneModel> lstphoneModel = new ArrayList();
        phoneModel.setId(1L);
        phoneModel.setNumber("1234567");
        phoneModel.setCitycode("1");
        phoneModel.setContrycode("57");
        lstphoneModel.add(phoneModel);
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Juan Rodriguez");
        userModel.setEmail("juan@rodriguez.org");
        userModel.setPassword("Nisum2024@");
        userModel.setPhone(lstphoneModel);
        return userModel;
    }
}
