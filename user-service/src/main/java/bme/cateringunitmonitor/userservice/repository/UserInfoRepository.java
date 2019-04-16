package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.userservice.dao.UserInfoDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoDAO, Long> {

    UserInfoDAO findByUsername(String username);
    boolean existsByUsername(String username);
}
