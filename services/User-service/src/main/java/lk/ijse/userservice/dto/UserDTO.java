package lk.ijse.userservice.dto;

import lk.ijse.userservice.dto.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private Role role;
    private String status;
}