package bme.cateringunitmonitor.userservice.repository;

import bme.cateringunitmonitor.api.dao.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
    boolean existsByUsername(String username);
}
