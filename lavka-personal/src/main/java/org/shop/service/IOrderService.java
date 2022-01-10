package org.shop.service;

import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;
import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;


import java.util.List;

public interface IOrderService {

    List<OrderDTO> getAllOrders(ClientDTO client);

    List<OrderDTO> getAllOrdersByIdClient(Long id);

    OrderDTO getInfoOrderByIdOrder(Long id);

    OrderDTO changeOrderStatusOrder(OrderDTO orderDTO);

    OrderDTO changeOrderStatusOrderId(Long id, OrderStatus status);

    OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook, boolean newAddress);

    OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook);



}
