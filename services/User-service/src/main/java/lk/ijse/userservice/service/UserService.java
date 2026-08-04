package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.UserDTO;

import java.util.List;


public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    String getUserByEmail(String email);

    UserDTO getUserObjectByemail(String email);

    List<UserDTO> getAllUsers();

    void updateUser(UserDTO userDTO);

    void deleteUser(String email);
}
