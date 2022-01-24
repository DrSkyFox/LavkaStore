package org.shop.pages;


import lombok.*;
import org.shop.dto.OrderDTO;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrdersPage extends DTOPage{

    private List<OrderDTO> orders;

    @Builder
    public OrdersPage(Integer pageNumber, Integer sizeOfPage, List<OrderDTO> orders) {
        super(pageNumber, sizeOfPage);
        this.orders = orders;
    }
}
