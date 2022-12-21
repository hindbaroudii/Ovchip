package P7;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
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
        testDAOHibernate();
    }


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
    public static void testDAOHibernate() {
        AdresDAOHibernate adao = new AdresDAOHibernate(getSession());
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(getSession());
        ProductDAOHibernate pdao = new ProductDAOHibernate(getSession());

        Reiziger reiziger = new Reiziger(5210, "H", "", "Baroudi", java.sql.Date.valueOf("2003-05-05"));
        Adres adres = new Adres(115, "7002CR", "138", "Bevrijdingsstraat", "Doetinchem", 50);
        OVChipkaart ovChipkaart = new OVChipkaart(6543210, java.sql.Date.valueOf("2029-09-10"), 3, 100.0, 50);
        Product product = new Product(115, "icons", "test test", 10.00);

        System.out.println(" --------  Reizigier DAO  --------\n");
        System.out.println("-- [TEST] find all -- \n -- All reizigers -- ");
        System.out.println(rdao.findAll()+"\n");

        System.out.println("-- [TEST] save --");
        System.out.println(rdao.save(reiziger));

        System.out.println("-- All reiziger na save --");
        System.out.println(rdao.findAll()+"\n");

        System.out.println("-- [TEST] update -- ");
        reiziger.setTussenvoegsel("TR");
        System.out.println(rdao.update(reiziger));

        System.out.println("-------------");
        System.out.println(rdao.findAll()+"\n");

        System.out.println("-- [TEST] reiziger find by id --");

        System.out.println(rdao.findById(reiziger.getReizigerid())+"\n");


        System.out.println("\n\n------- Adres DAO -------");
        System.out.println("-- [TEST] find all \n -- All adressen -- ");
        System.out.println(adao.findAll()+"\n");

        System.out.println("-- [TEST] save --");
        adao.save(adres);

        System.out.println("-- All adressen na save --");
        System.out.println(adao.findAll()+"\n");

        System.out.println("-- [TEST] update -- ");
        adres.setHuisnummer("79-1");
        adao.update(adres);

        System.out.println("-- [TEST] adres find by reiziger --");
        System.out.println(adao.findByReiziger(reiziger)+"\n");

        System.out.println("-- [TEST] delete --");
        adao.delete(adres);

        System.out.println("-- All adressen na delete --");
        System.out.println(adao.findAll());


        System.out.println("\n\n------- Product DAO -------");
        System.out.println("-- [TEST] find all \n -- All products -- ");
        System.out.println(pdao.findAll());

        System.out.println("-- [TEST] save --");
        pdao.save(product);

        System.out.println("-- All products na save --");
        System.out.println(pdao.findAll());

        System.out.println("-- [TEST] update -- ");
        product.setPrijs(20.00);

        System.out.println("-- All products na update --");
        System.out.println(pdao.findAll());


        System.out.println("\n\n------- OvChipkaart DAO -------");
        System.out.println("-- [TEST] save --");
        odao.save(ovChipkaart);

        System.out.println("-- [TEST] find by reiziger --");
        System.out.println(odao.findByReiziger(reiziger));


        System.out.println("-- [TEST] update -- ");
        ovChipkaart.setSaldo(600.0);
        odao.update(ovChipkaart);


        System.out.println(odao.findByReiziger(reiziger));



        System.out.println("\n\n----- DELETE TESTS -----");

        System.out.println("-- [TEST] delete ovchipkaart --");
        odao.delete(ovChipkaart);

        System.out.println("-- [TEST] delete product --");
        pdao.delete(product);
        System.out.println(pdao.findAll());
        System.out.println("---------");

        System.out.println("--- [TEST] delete reiziger ---");
        rdao.delete(reiziger);
        System.out.println(rdao.findAll());
    }
}