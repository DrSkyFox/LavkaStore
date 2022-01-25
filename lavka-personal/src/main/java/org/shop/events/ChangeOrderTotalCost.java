package org.shop.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.shop.db.persists.Client;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ChangeOrderTotalCost extends ApplicationEvent {


    private Long orderId;
    private Double newCost;
    private Client client;

    @Builder
    public ChangeOrderTotalCost(Object source, Long orderId, Double newCost, Client client) {
        super(source);
        this.orderId = orderId;
        this.newCost = newCost;
        this.client = client;
    }
}
