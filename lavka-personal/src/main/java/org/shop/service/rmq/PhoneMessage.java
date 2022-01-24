package org.shop.service.rmq;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneMessage extends RMQModelMessage{

    private Long clientId;
    private Long phoneNumber;


}
