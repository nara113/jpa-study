package jpabook.jpashop.domain;

import helloJpa2.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;
    private Address address;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
