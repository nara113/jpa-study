package helloJpa2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "favorite_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "address_history", joinColumns = @JoinColumn(name = "member_id"))
    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private List<AddressEntity> addresses = new ArrayList<>();
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
