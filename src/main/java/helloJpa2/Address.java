package helloJpa2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String street;
    private String zipcode;
    private String city;
}
