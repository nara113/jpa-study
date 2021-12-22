package helloJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private Long id;

//    @Column(unique = true, length = 10) //DDL 생성기능
//    private String name;

    @Column(name = "name", updatable = false, nullable = true)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob    //매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
    private String description;

    @Transient
    private String temp;
}
