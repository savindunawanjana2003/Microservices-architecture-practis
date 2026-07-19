package lk.ijse.userservice.service.impl;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.exception.DuplicateException;
import lk.ijse.userservice.repo.UserRepo;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.DataTypeConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final DataTypeConvertor convertor;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepo.findById(userDTO.getId()).isPresent())
            throw new DuplicateException("Duplicate User Id");
        return convertor.getUserDTO(userRepo.save(convertor.getUser(userDTO)));
    }

    @Override
    public String getUserByEmail(String email) {

        lk.ijse.userservice.entity.User user = (User) userRepo.findByEmail(email);
        System.out.println("user-------------> " + user);

        if (user.getEmail() == null || user.getEmail().equals("")) {

            return "401";

        } else {

            return "200";
        }
    }

}
