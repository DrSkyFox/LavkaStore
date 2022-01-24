package org.shop.service.rmq;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public abstract class RMQModelMessage {

    private Date dateCreate;

    public RMQModelMessage() {
        dateCreate = new Date();
    }


}
