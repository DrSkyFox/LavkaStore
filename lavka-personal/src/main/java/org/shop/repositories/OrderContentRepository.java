package org.shop.repositories;

import org.shop.entites.Order;
import org.shop.entites.OrderContent;
import org.shop.enums.OrderContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {

    List<OrderContent> getAllOrderContent(Order order);

    @Query("select oc from OrderContent oc where oc.contentStatus <> :status")
    List<OrderContent> getAllByOrderNotStatus(Order order, @Param("status") OrderContentStatus status);

    List<OrderContent> getAllOrderContentByOrderId(Long orderId);

    Optional<OrderContent> getOrderContentById(Long orderId);



}
