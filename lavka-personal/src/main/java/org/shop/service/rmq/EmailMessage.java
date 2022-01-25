package org.shop.service.rmq;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage extends RMQModelMessage{

    private Long clientId;
    private String email;
    private String message;



}
