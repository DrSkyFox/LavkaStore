package org.shop.entites;


import lombok.*;
import org.shop.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address_book")
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCreated;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @Column(name = "order_uid", nullable = false)
    private Integer orderUID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_book_id", nullable = false)
    private AddressBook addressBook;


}
