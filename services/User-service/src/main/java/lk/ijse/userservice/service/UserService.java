package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.UserDTO;


public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    String getUserByEmail(String email);
}
