package org.shop.dto;

import lombok.*;
import org.shop.db.persists.OrderContent;
import org.shop.enums.OrderContentStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderContentDTO {

    private Long id;

    private Long orderId;

    private Integer amount;

    private Double costPerOne;

    private Double discount;

    private Double totalCost;

    private OrderContentStatus contentStatus;

    private String canceledInformation;


    public OrderContentDTO(OrderContent orderContent) {
        this.id = orderContent.getId();
        this.orderId = orderContent.getOrder().getId();
        this.amount = orderContent.getAmount();
        this.costPerOne = orderContent.getCostPerOne();
        this.discount = orderContent.getDiscount();
        this.totalCost = orderContent.getTotalCost();
        this.contentStatus = orderContent.getContentStatus();
        this.canceledInformation = orderContent.getCanceledInformation();
    }


    public OrderContentDTO setAll(OrderContent orderContent) {
        this.id = orderContent.getId();
        this.orderId = orderContent.getOrder().getId();
        this.amount = orderContent.getAmount();
        this.costPerOne = orderContent.getCostPerOne();
        this.discount = orderContent.getDiscount();
        this.totalCost = orderContent.getTotalCost();
        this.contentStatus = orderContent.getContentStatus();
        this.canceledInformation = orderContent.getCanceledInformation();
        return this;
    }

}
