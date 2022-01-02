package jpql;

import jpabook.jpashop.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Memberr extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Teamm team;

    public Memberr(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
