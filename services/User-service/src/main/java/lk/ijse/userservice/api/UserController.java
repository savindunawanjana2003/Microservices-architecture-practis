package lk.ijse.userservice.api;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.exception.NotFoundException;
import lk.ijse.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody UserDTO userDTO) {
        System.out.println("============================+++++++0000000000000)))++++++++++++++++++++++++++ "+ userDTO.getPassword());
        System.out.println(userDTO.getRole());
        try {


            return ResponseEntity.ok(userService.saveUser(userDTO));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            ResponseEntity<Exception> body = ResponseEntity.badRequest().body(e);
            System.out.println(body);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("validate")
    public ResponseEntity<?> validate(@RequestBody UserDTO userDTO) {

        System.out.println(" ////" + userDTO);
        try {
            System.out.println("--------------------okkkkkkkkkkkk---------------");


            UserDTO user = userService.getUserObjectByemail(userDTO.getEmail());

            if (user.getPassword().equals(userDTO.getPassword())) {

            } else {
                throw new NotFoundException("Chack password again...!");
            }

            return ResponseEntity.ok(userService.getUserByEmail(userDTO.getEmail()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            ResponseEntity<Exception> body = ResponseEntity.badRequest().body(e);
            System.out.println(body);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("ProfailDeatiles")
    public ResponseEntity<?> getProfailDeatiles(@RequestParam String userEmail) {

        try {

            UserDTO user = userService.getUserObjectByemail(userEmail);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            ResponseEntity<Exception> body = ResponseEntity.badRequest().body(e);
            System.out.println(body);
            return ResponseEntity.internalServerError().build();
        }
    }


    // 4. Get All Users
    @GetMapping("getAll")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> allUsers = userService.getAllUsers(); // UserService eke me method eka thiyenna oni
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 5. Update User
    @PutMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(userDTO); // UserService eke updateUser method eka call karanawa
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 6. Delete User By Email
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(@RequestParam String userEmail) {
        try {
            userService.deleteUser(userEmail); // UserService eke deleteUser method eka call karanawa
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}