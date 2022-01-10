package org.shop.repositories;

import org.shop.entites.Order;
import org.shop.entites.OrderContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {


    List<OrderContent> findAllByOrder(Order order);

    @Query("select oc from OrderContent oc where oc.contentStatus <> org.shop.enums.OrderContentStatus.CANCELED")
    List<OrderContent> getAllByOrderNotStatus(Order order);

    List<OrderContent> getAllOrderContentByOrderId(Long orderId);

    Optional<OrderContent> getOrderContentById(Long orderId);



}
