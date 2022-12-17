package P7;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 * <p>
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        Session session = getSession();
        testFetchAll();
        testDAOHibernate(session);
    }


    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testDAOHibernate(Session session) {
        AdresDAO adresdao = new AdresDAOHibernate(session);
        OVChipkaartDAO ovdao = new OVChipkaartDAOHibernate(session);
        ProductDAO pdao = new ProductDAOHibernate(session);
        ReizigerDAO rdao = new ReizigerDAOHibernate(session);


        // Reiziger
        Reiziger reiziger = new Reiziger(1,"H","","Styles",Date.valueOf("1994-02-01"));
        Adres adres = new Adres(1,"6873LW","79-1","Utrechtstraat","Arnhem");
        OVChipkaart ov = new OVChipkaart(54321,Date.valueOf("2021-09-08"),2,60.0,1);
        Product product = new Product(123,"icons product","iconlevel",20.0);

        reiziger.setAdres(adres);
        ov.getProductList().add(product);
        reiziger.getOvChipkaartList().add(ov);

        System.out.println("---- Reiziger ----");

        System.out.println(" -- save -- ");
        System.out.println("save reiziger :\n" + rdao.save(reiziger));

        System.out.println(" -- find all -- ");
        System.out.println("all reizigers: \n"+ rdao.findAll());



    }
}
