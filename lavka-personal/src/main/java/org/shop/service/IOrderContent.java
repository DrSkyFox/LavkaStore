package org.shop.service;

import org.shop.dto.OrderContentDTO;
import org.shop.dto.OrderDTO;

import java.util.List;

public interface IOrderContent {


    OrderContentDTO getInfoOrderContent(Long id);
    OrderContentDTO changeOrderContent(OrderContentDTO orderContent);

    List<OrderContentDTO> getAllOrderContents(Long OrderId);
    List<OrderContentDTO> getAllOrderContents(OrderDTO order);



}
