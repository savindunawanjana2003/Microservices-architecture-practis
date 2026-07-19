package lk.ijse.userservice.repo;

import lk.ijse.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User,String> {


    List<User> findByEmail(String email);
}
