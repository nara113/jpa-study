package helloJpa2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE")
public abstract class Itemm {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
