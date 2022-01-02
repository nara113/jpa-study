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

            //엔티티 프로젝션 2 (조인)
            List<Team> resultList3 = em.createQuery("select t from Memberr m join m.team t", Team.class)
                    .getResultList();

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

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
