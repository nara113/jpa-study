package helloJpa2;

import lombok.AllArgsConstructor;
        import lombok.EqualsAndHashCode;
        import lombok.Getter;
        import lombok.NoArgsConstructor;

        import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class Address {
    private String street;
    private String zipcode;
    private String city;
}
