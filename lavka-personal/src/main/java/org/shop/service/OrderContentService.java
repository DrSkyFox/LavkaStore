package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.dto.OrderContentDTO;
import org.shop.dto.OrderDTO;
import org.shop.db.persists.Order;
import org.shop.db.persists.OrderContent;
import org.shop.exceptions.BadRequestParameters;
import org.shop.exceptions.OrderContentNotExists;
import org.shop.db.OrderContentRepository;
import org.shop.db.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderContentService implements IOrderContent {

    private final OrderRepository orderRepository;
    private final OrderContentRepository orderContentRepository;

    @Autowired
    public OrderContentService(OrderRepository orderRepository, OrderContentRepository orderContentRepository) {
        this.orderRepository = orderRepository;
        this.orderContentRepository = orderContentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderContentDTO getInfoOrderContent(Long id) {
        return orderContentRepository.getOrderContentById(id).map(OrderContentDTO::new).orElseThrow(OrderContentNotExists::new);
    }

    @Transactional
    @Override
    public OrderContentDTO changeOrderContent(OrderContentDTO orderContent) {
        OrderContent content = orderContentRepository.findById(orderContent.getId()).orElseThrow(OrderContentNotExists::new);
        Order order;
        if(orderContent.getId() == null) {
            throw new BadRequestParameters("Order number cannot be changed");
        }



        OrderContent fromDto = new OrderContent(orderContent);

        if (!content.equals(fromDto)) {
            log.info("Changing content of order - {} . One of params is changed (amount, cost per one, content status)", orderContent);
            content = calculateTotalCost(fromDto);
            content = orderContentRepository.save(content);
            log.info("Saved changes to DB. Start recalculate total cost of order");
            order = calculateTotalCost(content.getOrder());
            order = orderRepository.save(order);
            log.info("Order totalCost recalculated: - {}", order.getTotalCost());
        }
        return orderContent.setAll(content);
    }


    private OrderContent calculateTotalCost(OrderContent orderContent) {
        var totalCost = orderContent.getCostPerOne() * orderContent.getCostPerOne() * orderContent.getDiscount();
        orderContent.setTotalCost(totalCost);
        return orderContent;
    }

    private Order calculateTotalCost(Order order) {
        log.info("Order info : {}", order.toString());
        List<OrderContent> orderContent = orderContentRepository.getAllByOrderNotStatus(order);
        Double totalCost = 0.0;
        for (OrderContent content: orderContent
             ) {
            totalCost += content.getTotalCost();
        }
        order.setTotalCost(totalCost);
        return order;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderContentDTO> getAllOrderContents(Long orderId) {
        return getAllOrderContentsOfOrderID(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderContentDTO> getAllOrderContents(OrderDTO order) {
        return getAllOrderContentsOfOrderID(order.getId());
    }

    private List<OrderContentDTO> getAllOrderContentsOfOrderID(Long id) {
        return orderContentRepository.getAllOrderContentByOrderId(id).stream().map(OrderContentDTO::new).collect(Collectors.toList());
    }

}
