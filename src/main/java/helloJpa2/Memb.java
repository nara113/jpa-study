package helloJpa2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Memb {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private WorkPeriod workPeriod;

    private Address address;

    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode")),
            @AttributeOverride(name = "city", column = @Column(name = "work_city"))
    })
    private Address workAddress;
//
//    // 연관관계 편의 메소드
//    public void changeTeam(Team team) {
//        this.setTeam(team);
//        team.getMembers().add(this);
//    }

//    // 일대다 양방향
//    @ManyToOne
//    @JoinColumn(name = "team_id", insertable = false, updatable = false)
//    private Team team;
}
