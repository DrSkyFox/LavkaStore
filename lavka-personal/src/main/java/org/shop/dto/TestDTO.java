package org.shop.dto;

import lombok.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {

    private static final AtomicLong aLong = new AtomicLong(0);

    private Long id;
    private Date date;
    private String someText;


    public TestDTO(String someText) {
        this.id = aLong.incrementAndGet();
        this.date = new Date();
        this.someText = someText;
    }

}
