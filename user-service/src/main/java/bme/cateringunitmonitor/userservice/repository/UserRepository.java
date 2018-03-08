package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.entities.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean exists(User user);
}
