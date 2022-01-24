package org.shop.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.shop.enums.OrderStatus;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
@Setter
public class ChangeOrderStatus extends ApplicationEvent {

    private Long orderId;
    private String orderUID;
    private OrderStatus status;

    private Long clientId;
    private String fullName;
    private String emailOfClient;
    private Integer phoneNumber;

    @Builder
    public ChangeOrderStatus(Object source, Long orderId, String orderUID, OrderStatus status, Long clientId, String fullName, String emailOfClient, Integer phoneNumber) {
        super(source);
        this.orderId = orderId;
        this.orderUID = orderUID;
        this.status = status;
        this.clientId = clientId;
        this.fullName = fullName;
        this.emailOfClient = emailOfClient;
        this.phoneNumber = phoneNumber;
    }
}
