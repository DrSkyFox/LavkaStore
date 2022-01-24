package org.shop.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.shop.db.persists.AddressBook;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
@Setter
public class ChangeOrderAddress extends ApplicationEvent {

    private Long orderId;
    private AddressBook addressBook;
    private String string;

    @Builder
    public ChangeOrderAddress(Object source, Long orderId, AddressBook addressBook, String string) {
        super(source);
        this.orderId = orderId;
        this.addressBook = addressBook;
        this.string = string;
    }
}
