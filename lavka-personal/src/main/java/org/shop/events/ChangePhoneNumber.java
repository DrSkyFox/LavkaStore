package org.shop.events;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
@Setter
public class ChangePhoneNumber extends ApplicationEvent {

    private Long clientId;
    private Integer phoneNumber;

    public ChangePhoneNumber(Object source, Long clientId, Integer phoneNumber) {
        super(source);
        this.clientId = clientId;
        this.phoneNumber = phoneNumber;
    }

}
