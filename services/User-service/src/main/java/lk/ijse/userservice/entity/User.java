package lk.ijse.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lk.ijse.userservice.dto.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private Role role;

    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


