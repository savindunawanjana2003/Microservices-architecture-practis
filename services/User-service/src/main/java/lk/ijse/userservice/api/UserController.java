package lk.ijse.userservice.api;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.ok(userService.saveUser(userDTO));
        }catch (Exception e){
            System.err.println(e.getMessage());
            ResponseEntity<Exception> body = ResponseEntity.badRequest().body(e);
            System.out.println(body);
            return ResponseEntity.internalServerError().build();
        }
    }
}
