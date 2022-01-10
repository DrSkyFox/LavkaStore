package org.shop.entites;


import lombok.*;
import org.shop.dto.OrderContentDTO;
import org.shop.enums.OrderContentStatus;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_content")
public class OrderContent {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false, length = 6)
    private Integer amount;

    @Column(name = "costPerOne", nullable = false)
    private Double costPerOne;

    @Column(name = "discount", nullable = false)
    private Double discount;

    @Column(name = "totalCost", nullable = false)
    private Double totalCost;

    @Column(name = "contentStatus", nullable = false)
    private OrderContentStatus contentStatus;

    @Column(name = "canceledInformation")
    private String canceledInformation;


    public OrderContent(OrderContentDTO orderContent) {
        setAll(orderContent);
    }

    public OrderContent setAll(OrderContentDTO orderContent) {
        this.id = order.getId();
        this.order = orderContent.getOrderId();
        this.amount = orderContent.getAmount();
        this.costPerOne = orderContent.getCostPerOne();
        this.discount = orderContent.getDiscount();
        this.totalCost = orderContent.getTotalCost();
        this.contentStatus = orderContent.getContentStatus();
        this.canceledInformation = orderContent.getCanceledInformation();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderContent that = (OrderContent) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order) && Objects.equals(amount, that.amount) && Objects.equals(costPerOne, that.costPerOne) && Objects.equals(discount, that.discount) && Objects.equals(totalCost, that.totalCost) && contentStatus == that.contentStatus && Objects.equals(canceledInformation, that.canceledInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, amount, costPerOne, discount, totalCost, contentStatus, canceledInformation);
    }
}
