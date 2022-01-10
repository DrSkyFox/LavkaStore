package org.shop.service;

import org.shop.dto.OrderContentDTO;
import org.shop.dto.OrderDTO;
import org.shop.repositories.OrderContentRepository;
import org.shop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderContentService implements IOrderContent {

    private final OrderRepository orderRepository;
    private final OrderContentRepository orderContentRepository;

    @Autowired
    public OrderContentService(OrderRepository orderRepository, OrderContentRepository orderContentRepository) {
        this.orderRepository = orderRepository;
        this.orderContentRepository = orderContentRepository;
    }


    @Override
    public OrderContentDTO getInfoOrderContent(Long id) {
        return null;
    }

    @Override
    public OrderContentDTO changeOrderContent(OrderContentDTO orderContent) {
        return null;
    }

    @Override
    public List<OrderContentDTO> getAllOrderContents(Long OrderId) {
        return null;
    }

    @Override
    public List<OrderContentDTO> getAllOrderContents(OrderDTO order) {
        return null;
    }
}
