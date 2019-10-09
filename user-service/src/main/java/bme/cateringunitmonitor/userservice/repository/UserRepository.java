package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.userservice.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, Long> {

    UserDAO findByUsername(String username);
    int deleteByUsername(String username);
    boolean existsByUsername(String username);
}
