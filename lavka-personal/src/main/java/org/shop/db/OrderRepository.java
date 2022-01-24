package org.shop.db;

import org.shop.db.persists.Order;
import org.shop.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = "select o from orders where o.orderStatus in ?1", nativeQuery = true, countQuery = "select count(*) from orders o where o.orderStatus in ?1")
    Page<Order> findAllByStatus(List<OrderStatus> status, Pageable pageable);

    @Query(value = "select o from orders where o.orderStatus in ?1 and o.created_date between ?2 and ?3", nativeQuery = true, countQuery = "select count(*) from orders where o.orderStatus in ?1 and o.created_date between ?2 and ?3")
    Page<Order> findAllByStatusBetweenDates(List<OrderStatus> status, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select o from orders where o.client_id = ?1 and o.orderStatus in ?2", nativeQuery = true, countQuery = "select o from orders where o.client_id = ?1 and o.orderStatus in ?2")
    Page<Order> findAllByStatusForClient(Long clientId, List<OrderStatus> status, Pageable pageable);

    @Query(value = "select o from orders where o.client_id = ?1 and o.orderStatus in ?2 and o.created_date between ?3 and ?4", nativeQuery = true, countQuery = "select o from orders where o.client_id = ?1 and o.orderStatus in ?2 and o.created_date between ?3 and ?4")
    Page<Order> findAllByStatusForClientBetweenDates(Long id, List<OrderStatus> status, Date startDate, Date endDate, Pageable pageable);



}

