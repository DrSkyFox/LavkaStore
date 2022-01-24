package org.shop.responses;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    private Date date;
    private String msg;
    private Integer code;


    public ResponseError(String msg, Integer code) {
        this.date = new Date();
        this.msg = msg;
        this.code = code;
    }


}
