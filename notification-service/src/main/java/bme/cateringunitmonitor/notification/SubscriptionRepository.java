package bme.cateringunitmonitor.notification;

import bme.cateringunitmonitor.notification.dao.SubscriptionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import rx.Subscription;

import java.util.List;
import java.util.Optional;


public interface SubscriptionRepository extends JpaRepository<SubscriptionDAO, Long> {
    Optional<SubscriptionDAO> findByCateringUnitNameAndUsername(String cateringUnitName, String username);
    List<SubscriptionDAO> findAllByCateringUnitName(String cateringUnitName);
    int deleteAllByCateringUnitName(String cateringUnitName);
    int deleteAllByUsername(String username);
}
