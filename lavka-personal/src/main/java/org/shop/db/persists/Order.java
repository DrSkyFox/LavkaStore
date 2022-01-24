package org.shop.db.persists;


import lombok.*;
import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCreated;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "orderStatus")
    private OrderStatus orderStatus;

    @Column(name = "order_uid", nullable = false)
    private String orderUID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_book_id", nullable = false)
    private AddressBook addressBook;

    @Column(name = "totalCost", nullable = false)
    private Double totalCost;

    public Order(OrderDTO order) {
        setAll(order);
    }

    public Order setAll(OrderDTO order) {
        this.id = order.getId();
        this.orderCreated = order.getOrderCreated();
        this.orderStatus = order.getOrderStatus();
        this.orderUID = order.getOrderUID();
        this.totalCost = order.getTotalCost();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(client, order.client) && Objects.equals(orderCreated, order.orderCreated) && orderStatus == order.orderStatus && Objects.equals(orderUID, order.orderUID) && Objects.equals(addressBook, order.addressBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, orderCreated, orderStatus, orderUID, addressBook);
    }
}
