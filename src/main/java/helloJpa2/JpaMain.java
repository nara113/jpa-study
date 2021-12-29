package helloJpa2;

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
//            method1(em);

            Movie movie = new Movie();
            movie.setName("gram");
            movie.setPrice(123);
            movie.setActor("A");

            em.persist(movie);

            em.flush();
            em.clear();

            Itemm itemm = em.find(Itemm.class, movie.getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void method1(EntityManager em) {
        Team team = new Team();
        team.setName("teamA");

        em.persist(team);

        Memb memb = new Memb();
        memb.setName("memberA");

        em.persist(memb);

        team.getMembers().add(memb);
    }
}
