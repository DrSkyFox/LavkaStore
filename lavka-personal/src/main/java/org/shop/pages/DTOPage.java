package org.shop.pages;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class DTOPage {

    private Integer pageNumber;
    private Integer sizeOfPage;

}
