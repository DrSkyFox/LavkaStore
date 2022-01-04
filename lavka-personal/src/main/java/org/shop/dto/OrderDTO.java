package org.shop.dto;

import lombok.*;
import org.shop.entites.AddressBook;
import org.shop.entites.Client;
import org.shop.entites.Order;
import org.shop.enums.OrderStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private Client client;

    private Date orderCreated;

    private OrderStatus orderStatus;

    private Integer orderUID;

    private AddressBook addressBook;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.client = order.getClient();
        this.orderCreated = order.getOrderCreated();
        this.orderStatus = order.getOrderStatus();
        this.orderUID = order.getOrderUID();
        this.addressBook = order.getAddressBook();
    }
}
