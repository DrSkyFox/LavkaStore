package org.shop.entites;


import lombok.*;
import org.shop.dto.ClientDTO;
import org.shop.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", length = 64)
    private String firstName;

    @Column(name = "secondName", length = 64)
    private String secondName;

    @Column(name = "phoneNumber", length = 15)
    private Integer phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "subscribed")
    private Boolean subscribed;

    @Column(name ="notifications")
    private Boolean notifications;

    @Column(name = "status")
    private Status status;

    @Column(name = "registrationDate")
    private Date registrationDate;


    public Client(ClientDTO clientDTO) {
        this.id = clientDTO.getId();
        this.firstName = clientDTO.getFirstName();
        this.secondName = clientDTO.getSecondName();
        this.phoneNumber = clientDTO.getPhoneNumber();
        this.email = clientDTO.getEmail();
        this.subscribed = clientDTO.getSubscribed();
        this.notifications = clientDTO.getNotifications();
        this.status = clientDTO.getStatus();
    }
}
