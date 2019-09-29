package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.userservice.dao.UserInfoDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoDAO, Long> {

    Optional<UserInfoDAO> findUserInfoByUsername(String username);
    int deleteByUsername(String username);
}
