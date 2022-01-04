package org.shop.dto;


import lombok.*;
import org.shop.entites.Client;
import org.shop.enums.Status;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String firstName;
    private String secondName;
    private Integer phoneNumber;
    private String email;
    private Boolean subscribed;
    private Boolean notifications;
    private Status status;
    private Date registrationDate;


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.secondName = client.getSecondName();
        this.phoneNumber = client.getPhoneNumber();
        this.email = client.getEmail();
        this.subscribed = client.getSubscribed();
        this.notifications = client.getNotifications();
        this.status = client.getStatus();
        this.registrationDate = client.getRegistrationDate();
    }



}
