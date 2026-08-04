package lk.ijse.userservice.service.impl;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.exception.DuplicateException;
import lk.ijse.userservice.exception.NotFoundException;
import lk.ijse.userservice.repo.UserRepo;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.DataTypeConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final DataTypeConvertor convertor;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        System.out.println("===============+++++++++++++++++++++++/////++ "+userDTO.getPassword());
        if (userRepo.findById(userDTO.getId()).isPresent()) {
            throw new DuplicateException("Duplicate User Id");

        }
        return convertor.getUserDTO(userRepo.save(convertor.getUser(userDTO)));
    }

    @Override
    public String getUserByEmail(String email) {


        Optional<User> user = userRepo.findByEmail(email);
        User user2 = user.get();

        System.out.println("================service=========== " + user2);


        if (user2 == null) {

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

    @Override
    public UserDTO getUserObjectByemail(String email) {

        Optional<User> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {

            throw new NotFoundException("User not found please Register first");

        }

        UserDTO userDTO = convertor.getUserDTO(user.get());

        return userDTO;
    }

    // 4. Get All Users
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream()
                .map(convertor::getUserDTO)
                .collect(Collectors.toList());
    }

    // 5. Update User
    @Override
    public void updateUser(UserDTO userDTO) {
        // 1. Existing user DB eken allanawa
        User existingUser = userRepo.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found to update for email: " + userDTO.getEmail()));

        // 2. Values tika update karanawa (createdAt & updatedAt ewwa manual set karanna oni NA!)
        existingUser.setName(userDTO.getName());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setRole(userDTO.getRole());
        existingUser.setStatus(userDTO.getStatus());

        // Manual time update ekak oni mamawath danna puluwan (Normally @UpdateTimestamp does this automatically):
        existingUser.setUpdatedAt(LocalDateTime.now());
        // 3. Save karaddi Hibernate eken @UpdateTimestamp hindama updatedAt eka auto update wenawa
        userRepo.save(existingUser);
    }

    // 6. Delete User By Email
    @Override
    public void deleteUser(String email) {
        User existingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found to delete for email: " + email));

        userRepo.delete(existingUser);
    }

}
