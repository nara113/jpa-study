package helloJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //JPA에서 리플렉션을 사용하기 때문에 필요
public class Member {
    @Id
    private Long id;
    private String name;
}
