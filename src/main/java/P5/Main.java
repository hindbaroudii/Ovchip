package P5;
;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    public static Connection getConnection() {
        try{
            final String url = "jdbc:postgresql://localhost/ovchip";
            final String user = "postgres";
            final String password = "zxcv";

            connection = DriverManager.getConnection( url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException{
        connection.close();
    }

    public static void testProductDAO(ProductDAO pdao, ReizigerDAO rdao, OVChipkaartDAO odao){
        System.out.println("--- [TEST] PDAO find All ---\n");
        System.out.println(pdao.findall());
        System.out.println("-------\n");

        Reiziger reiziger = new Reiziger(54312,"H","","Baroudi",Date.valueOf("2003-05-05"));
        OVChipkaart ovChipkaart =new OVChipkaart(654232,Date.valueOf("2027-09-05"),2,50.0, reiziger.getId());
        OVChipkaart ovkaart = new OVChipkaart(98276,Date.valueOf("2028-04-09"),2,40.0, reiziger.getId());
        Product product = new Product(5423,"test","testp5",10.0);

        System.out.println("-- [TEST] SAVE --\n");
        System.out.println("== save reizigr ==;");
        rdao.save(reiziger);

        System.out.println("== save ovchipkaart ==");
        odao.save(ovChipkaart);
        odao.save(ovkaart);


        ovkaart.getProducts().add(product);

        product.getOvChipkaarten().add(ovChipkaart);
        product.getOvChipkaarten().add(ovkaart);

        System.out.println("== save product ==\n");
        pdao.save(product);

        System.out.println(pdao.findall());
        System.out.println("-------\n");

        System.out.println("-- [TEST] UPDATE --");
        System.out.println("-- before --");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));

        System.out.println("-- na --");
        product.setNaam("new product");
        pdao.update(product);

        System.out.println(pdao.findByOVChipkaart(ovChipkaart));

        System.out.println("-- [TEST] DELETE --");
        pdao.delete(product);
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));
        System.out.println(pdao.findall());

    }

    public static void testAdresDAO(AdresDAOPsql adao, ReizigerDAOPsql rdao){
        System.out.println("----- [TEST] AdresDAO -----");
        System.out.println("-- [TEST] find all --");
        List<Adres> adresList = adao.findAll();
        for(Adres a : adresList){
            System.out.println(a);
        }
        System.out.println("\n-- [TEST] save --");
        System.out.println("-- Er zijn " + adresList.size() + " adressen:");
        Reiziger reiziger = new Reiziger(6327,"H","","Baroudi",Date.valueOf("2003-05-05"));
        rdao.save(reiziger);

        Adres adres = new Adres(6440,"6847LW","79-1","Utrechtstraat","Arnhem", reiziger.getId());
        adao.save(adres);
        adresList = adao.findAll();
        System.out.println("-- Na  " + adresList.size() + " adressen, na save():");

        System.out.println("--------");

        System.out.println("\n --- [TEST] findByReiziger ---");

        Reiziger reiziger1 = new Reiziger(7232,"Z","","Trifi",Date.valueOf("1999-05-18"));
        rdao.save(reiziger1);

        Adres adres1 = new Adres(743,"4565XS", "117","vuurdoornstraat","Opheusden", reiziger1.getId());
        adao.save(adres1);

        System.out.println(adao.findByReiziger(reiziger1));
        System.out.println("--------");

        System.out.println("\n--- [TEST] UPDATE ---");
        Reiziger reiziger2 = new Reiziger(89,"H","","Murad",Date.valueOf("1999-01-08"));
        rdao.save(reiziger2);

        Adres adres2 = new Adres(24,"9879NR","78","akkerstraat","Delft", reiziger2.getId());
        adao.save(adres2);

        adres2.setHuisnummer("56");
        adao.update(adres2);
        System.out.println(adao.findAll());

        System.out.println("\n--- [TEST] DELETE ---");
        Reiziger  reiziger3 = new Reiziger(45,"L","","Messi",Date.valueOf("1986-09-04"));
        Adres adres3 = new Adres(46,"8742NE","32","voetbalstraat","PARIS",reiziger3.getId());

        rdao.save(reiziger3);
        adao.save(adres3);
        System.out.println("\n -- voor delete --");
        System.out.println(adao.findByReiziger(reiziger3));
        System.out.println("\n -- na delete");
        adao.delete(adres3);
        System.out.println(adao.findByReiziger(reiziger3));


    }

    public static void testOVChipkaartDAO(ReizigerDAO rdao,OVChipkaartDAO odao){
        System.out.println("\n--- TEST OvchipkaartDAO ---");
        System.out.println("\n --- [TEST] SAVE ---");
        Reiziger reiziger = new Reiziger(230,"Z","","Trifi",Date.valueOf("1999-05-18"));
        Adres adres = new Adres(239,"9879NR","78","akkerstraat","Delft", reiziger.getId());
        OVChipkaart ov = new OVChipkaart(98082,Date.valueOf("2032-09-3"),2,60.00,reiziger.getId());

        reiziger.setAdres(adres);
        reiziger.getOvChipkaarten().add(ov);
        rdao.save(reiziger);

        System.out.println(odao.findByReiziger(reiziger));
        System.out.println("\n --- [TEST] UPDATE ---");
        System.out.println("\n voor update,"+odao.findByReiziger(reiziger));
        ov.setSaldo(40.0);
        rdao.update(reiziger);
        System.out.println("\n na update," + odao.findByReiziger(reiziger));

        System.out.println("\n --- [TEST] findbyreiziger ---");
        System.out.println(odao.findByReiziger(reiziger));

        System.out.println("\n--- [TEST] DELETE ---");
        rdao.delete(reiziger);
        odao.findByReiziger(reiziger);


    }









    public static void main (String [] args) throws SQLException{
        Connection connection = getConnection();
        ProductDAOPsql pdao = new ProductDAOPsql(connection);
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        odao.setPdao(pdao);
        testProductDAO(pdao,rdao,odao);
        testAdresDAO(adao,rdao);
        testOVChipkaartDAO(rdao,odao);
        closeConnection(connection);


    }
}