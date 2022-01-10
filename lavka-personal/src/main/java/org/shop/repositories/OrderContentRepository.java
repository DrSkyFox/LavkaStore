package org.shop.repositories;

import org.shop.entites.Order;
import org.shop.entites.OrderContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderContentRepository extends JpaRepository<OrderContent, Long> {

    List<OrderContent> getAllOrderContent(Order order);
    List<OrderContent> getAllOrderContentByOrderId(Long orderId);

    Optional<OrderContent> getOrderContentById(Long orderId);



}
