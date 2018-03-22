package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.entities.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    int deleteByUsername(String username);
    User save(User user);
}
