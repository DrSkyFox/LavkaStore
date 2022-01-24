package org.shop.db;

import org.shop.db.persists.Client;
import org.shop.db.persists.Order;
import org.shop.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o")
    List<Order> findALl();

    List<Order> findAllByOrderStatus(OrderStatus status);

    List<Order> findAllByClient(Client client);

    List<Order> findAllByClientId(Long id);

    Optional<Order> findById(Long id);

    @Query("select o from Order o where o.orderUID = :uuid")
    Optional<Order> findOrderByUID(@Param("uuid") String uuid);

    @Query("select o from Order o where o.orderCreated between :startDate and :endDate")
    Optional<Order> findOrderBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Optional<Order> findByOrderCreated(Date date);

    Optional<Order> findByClient(Client client);

    Optional<Order> findByClientId(Long id);

}

