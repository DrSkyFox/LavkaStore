package org.shop.service;

import org.shop.dto.AddressBookDTO;

import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;
import org.shop.pages.OrdersPage;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

public interface IOrderService {

    OrdersPage getAllOrders(List<OrderStatus>  status , Pageable pageable);

    OrdersPage getAllOrdersByPagesAndClientId(Long clientId, List<OrderStatus>  status, Pageable pageable);

    OrdersPage getAllOrdersByPagesAndClientIdAndBetweenDate(Long clientId, List<OrderStatus>  status, Date startDate, Date endDate, Pageable pageable);

    OrderDTO getInfoOrderByIdOrder(Long id);

    OrderDTO changeOrderStatusOrder(OrderDTO orderDTO);

    OrderDTO changeOrderStatusOrderId(Long id, OrderStatus status);

    OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook, boolean newAddress);

    OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook);



}
