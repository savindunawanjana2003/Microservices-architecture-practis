package lk.ijse.userservice.service.impl;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.exception.DuplicateException;
import lk.ijse.userservice.repo.UserRepo;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.DataTypeConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final DataTypeConvertor convertor;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepo.findById(userDTO.getId()).isPresent()) {
            throw new DuplicateException("Duplicate User Id");

        }
        return convertor.getUserDTO(userRepo.save(convertor.getUser(userDTO)));
    }

    @Override
    public String getUserByEmail(String email) {

        Optional<User> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {

            return "500";
        }

        User user1 = user.get();
        System.out.println("user-------------> " + user);

        if (user1.getEmail() == null || user1.getEmail().equals("")) {

            return "401";

        } else {

            return "200";
        }
    }

}
