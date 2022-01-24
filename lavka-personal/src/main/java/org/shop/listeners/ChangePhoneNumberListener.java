package org.shop.listeners;

import org.shop.events.ChangePhoneNumber;
import org.springframework.context.ApplicationListener;

public class ChangePhoneNumberListener implements ApplicationListener<ChangePhoneNumber> {


    @Override
    public void onApplicationEvent(ChangePhoneNumber event) {

    }
}
