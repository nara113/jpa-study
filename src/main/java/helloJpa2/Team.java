package helloJpa2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Memb> members = new ArrayList<>();

//    // 연관관계 편의 메소드 (member or team 한군데만 작성)
//    public void addMember(Memb member) {
//        member.setTeam(this);
//        members.add(member);
//    }

//    @OneToMany
//    @JoinColumn(name = "team_id")
//    private List<Memb> members = new ArrayList<>();
}
