package org.shop.dto;

import lombok.*;
import org.shop.db.persists.AddressBook;
import org.shop.db.persists.Order;
import org.shop.enums.OrderStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private Long clientId;

    private Date orderCreated;

    private OrderStatus orderStatus;

    private String orderUID;

    private AddressBook addressBook;

    private Double totalCost;

    public OrderDTO(Order order) {
        setAll(order);
    }


    public OrderDTO setAll(Order order) {
        this.id = order.getId();
        this.clientId = order.getClient().getId();
        this.orderCreated = order.getOrderCreated();
        this.orderStatus = order.getOrderStatus();
        this.orderUID = order.getOrderUID();
        this.addressBook = order.getAddressBook();
        this.totalCost = order.getTotalCost();
        return this;
    }


}
