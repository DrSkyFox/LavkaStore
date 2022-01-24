package org.shop.db.persists;


import lombok.*;
import org.shop.dto.ClientDTO;
import org.shop.enums.Status;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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

    @Column(name = "emailIsVerified")
    private Boolean emailIsVerified = false;

    @Column(name="phoneIsVerified")
    private Boolean phoneIsVerified = false;


    public Client(ClientDTO clientDTO) {
        setAll(clientDTO);
    }

    public Client setAll(ClientDTO client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.secondName = client.getSecondName();
        this.phoneNumber = client.getPhoneNumber();
        this.email = client.getEmail();
        this.subscribed = client.getSubscribed();
        this.notifications = client.getNotifications();
        this.status = client.getStatus();
        this.registrationDate = client.getRegistrationDate();
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(secondName, client.secondName) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(email, client.email) && Objects.equals(subscribed, client.subscribed) && Objects.equals(notifications, client.notifications) && status == client.status && Objects.equals(registrationDate, client.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, phoneNumber, email, subscribed, notifications, status, registrationDate);
    }
}
