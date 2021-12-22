package helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            //쓰기 지연
            em.persist(new Member(1L, "A"));
            em.persist(new Member(2L, "B"));
            em.persist(new Member(3L, "B"));
            em.persist(new Member(4L, "B"));

            Member member = em.find(Member.class, 1L);

            //변경 감지
            member.setName("zzz");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
