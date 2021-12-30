package helloJpa2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            method1(em);
//            method2(em);
//            method3(em);

            Parent parent = new Parent();

            Child c1 = new Child();
            Child c2 = new Child();

            // cascade ALL : c1, c2 도 persistk
            parent.getChildren().add(c1);
            parent.getChildren().add(c2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent p1 = em.find(Parent.class, parent.getId());
            p1.getChildren().remove(0); //orphanRemoval = true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void method3(EntityManager em) {
        Team t1 = new Team();
        t1.setName("T1");
        em.persist(t1);

        Team t2 = new Team();
        t2.setName("T2");
        em.persist(t2);

        Memb m1 = new Memb();
        m1.setName("M1");
        m1.setTeam(t1);
        em.persist(m1);

        Memb m2 = new Memb();
        m2.setName("M2");
        m2.setTeam(t2);
        em.persist(m2);

        em.flush();
        em.clear();

        List<Memb> resultList = em.createQuery("select m from Memb m join fetch m.team", Memb.class)
                .getResultList();
    }

    private static void method2(EntityManager em) {
        Movie movie = new Movie();
        movie.setName("gram");
        movie.setPrice(123);
        movie.setActor("A");

        em.persist(movie);

        em.flush();
        em.clear();

        Itemm itemm = em.find(Itemm.class, movie.getId());
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
