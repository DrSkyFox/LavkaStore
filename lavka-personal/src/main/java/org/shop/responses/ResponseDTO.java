package org.shop.responses;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Date date;

    private String info;

    private ResponseStatus status;

}
