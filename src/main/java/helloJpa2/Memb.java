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

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // 연관관계 편의 메소드
    public void changeTeam(Team team) {
        this.setTeam(team);
        team.getMembers().add(this);
    }
}
