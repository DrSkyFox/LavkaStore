package org.shop.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
@Setter

public class ChangeEmail extends ApplicationEvent {

    private Long clientId;
    private String email;

    @Builder
    public ChangeEmail(Object source, Long clientId, String email) {
        super(source);
        this.clientId = clientId;
        this.email = email;
    }
}
