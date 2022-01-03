package org.shop.entites;


import lombok.*;
import org.shop.enums.OrderContentStatus;

import javax.persistence.*;

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
    private Order orderId;

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

}
