package jpql;

import helloJpa2.Team;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Teamm t1 = new Teamm();
            t1.setName("t1");
            em.persist(t1);

            Teamm t2 = new Teamm();
            t2.setName("t2");
            em.persist(t2);

            Memberr m1 = new Memberr();
            m1.setTeam(t1);
            em.persist(m1);

            Memberr m2 = new Memberr();
            m2.setTeam(t1);
            em.persist(m2);

            Memberr m3 = new Memberr();
            m3.setTeam(t2);
            em.persist(m3);

            em.flush();
            em.clear();

            List<Memberr> resultList = em.createQuery("select m from Memberr m", Memberr.class)
                    .getResultList();

            for (Memberr m: resultList) {
                //N + 1 문제
                System.out.println(m.getName() + " " + m.getTeam().getName());
            }

            //다대일
            //FetchType.LAZY 상관없이 즉시 로딩
            List<Memberr> resultList2 = em.createQuery("select m from Memberr m join fetch m.team", Memberr.class)
                    .getResultList();

            for (Memberr m: resultList) {
                System.out.println(m.getName() + " " + m.getTeam().getName());
            }

            //일대다
            List<Teamm> resultList3 = em.createQuery("select t from Teamm t join fetch t.members", Teamm.class)
                    .getResultList();

            for (Teamm t: resultList3) {
                //t1 두번 출력
                System.out.println(t.getName() + " " + t.getMembers().size());
            }

            //일대다
            List<Teamm> resultList4 = em.createQuery("select distinct t from Teamm t join fetch t.members", Teamm.class)
                    .getResultList();

            for (Teamm t: resultList4) {
                System.out.println(t.getName() + " " + t.getMembers().size());
            }

            //엔티티 직접 사용 - 기본키, 외래키
            Memberr member = em.createQuery("select m from Memberr m where m = :member", Memberr.class)
                    .setParameter("member", m1)
                    .getSingleResult();

            System.out.println(member);

            List<Memberr> members = em.createQuery("select m from Memberr m where m.team = :team", Memberr.class)
                    .setParameter("team", t1)
                    .getResultList();

            for (Memberr memberr : members) {
                System.out.println(memberr);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void method1(EntityManager em) {
        Memberr memberr = new Memberr();
        memberr.setName("memberA");
        memberr.setAge(20);
        memberr.setCreatedDate(LocalDateTime.now());

        em.persist(memberr);

        em.flush();
        em.clear();

        //타입을 알때
        TypedQuery<Memberr> membersQuery = em.createQuery("select m from Memberr m where m.name = :username", Memberr.class);
        membersQuery.setParameter("username","memberA");

        //결과 없어도 예외 발생 안함
        List<Memberr> resultList = membersQuery.getResultList();

        //결과는 무조건 하나 (예외 발생)
        Memberr singleResult = membersQuery.getSingleResult();

        //타입을 모를때
        Query memberIdAndNameQuery = em.createQuery("select m.id, m.name from Memberr m");

        em.flush();
        em.clear();

        //엔티티 프로젝션 1 (조인)
        List<Team> resultList1 = em.createQuery("select m.team from Memberr m", Team.class)
                .getResultList();

        System.out.println("========================!!");
        //엔티티 프로젝션 2 (조인)
        List<Team> resultList3 = em.createQuery("select t from Memberr m join m.team t", Team.class)
                .getResultList();
        System.out.println("========================!!");

        //스칼라 타입 프로젝션 1
        List resultList2 = em.createQuery("select m.name, m.age from Memberr m")
                .getResultList();

        Object object = resultList2.get(0);
        Object[] result = (Object[]) object;
        System.out.println(result[0]);
        System.out.println(result[1]);

        //스칼라 타입 프로젝션 2
        List<Object[]> resultList4 = em.createQuery("select m.name, m.age from Memberr m").getResultList();
        Object[] objects = resultList4.get(0);
        System.out.println(objects[0]);
        System.out.println(objects[1]);

        //스칼라 타입 프로젝션 3
        List<MemberDTO> resultList5 = em.createQuery("select new jpql.MemberDTO(m.name, m.age) from Memberr m", MemberDTO.class)
                .getResultList();

        MemberDTO memberDTO = resultList5.get(0);
        System.out.println(memberDTO.getAge());
        System.out.println(memberDTO.getName());
    }
}
