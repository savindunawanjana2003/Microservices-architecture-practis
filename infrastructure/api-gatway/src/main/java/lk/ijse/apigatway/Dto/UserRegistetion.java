package lk.ijse.apigatway.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistetion {
    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String role;
    private String status;
}
