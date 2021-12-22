package helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            //비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("hi");

            //영속
            em.persist(member);

            //1차 캐시에서 가져옴
            Member member1 = em.find(Member.class, 100L);
            Member member2 = em.find(Member.class, 100L);

            //true (REPEATABLE READ 격리 수준을 애플리케이션 차원에서 제공)
            System.out.println(member1 == member2);

            //커밋 시점에 SQL을 DB로 보낸다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
