package helloJpa2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Memb m = new Memb();
            m.getFavoriteFoods().add("apple");  //값타입 컬렉션은 cascade, orphanRemoval 자동 적용

            m.setAddress(new Address("A","B","C"));

            m.getAddressHistory().add(new Address("a","a","a"));
            m.getAddressHistory().add(new Address("b","b","b"));

            m.getAddresses().add(new AddressEntity(new Address("a","a","a")));
            m.getAddresses().add(new AddressEntity(new Address("b","b","b")));

            em.persist(m);

            em.flush();
            em.clear();

            Memb member = em.find(Memb.class, m.getId());

            //Set 값타입 수정
            member.getFavoriteFoods().remove("apple");  //delete 쿼리
            member.getFavoriteFoods().add("orange");    //insert 쿼리

//            member.getAddress().setStreet("newA");   //값타입은 불변
            member.setAddress(new Address("newA","B","C"));

            //Address 값타입 컬렉션 수정
            //address_history 테이블에서 member_id 전체 삭제후 insert (사용하면 안됨)
            member.getAddressHistory().remove(new Address("a","a","a"));
            member.getAddressHistory().add(new Address("newA","a","a"));

            member.getAddresses().remove(0);
            member.getAddresses().add(new AddressEntity(new Address("newA","a","a")));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void method6() {
        WorkPeriod w1 = new WorkPeriod(LocalDateTime.MAX, LocalDateTime.MAX);
        WorkPeriod w2 = new WorkPeriod(LocalDateTime.MAX, LocalDateTime.MAX);

        System.out.println("w1 == w2 : " + (w1 == w2));
        System.out.println("w1 equals w2 : " + (w1.equals(w2))); //값타입은 동등성비교 (equals 재정의)
    }

    private static void method5(EntityManager em) {
        WorkPeriod workPeriod = new WorkPeriod(LocalDateTime.now(), LocalDateTime.now());

        Address address = new Address("street", "zipcode", "city");

        Memb m = new Memb();
        m.setWorkPeriod(workPeriod);
        m.setAddress(address);

        em.persist(m);
    }

    private static void method4(EntityManager em) {
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
