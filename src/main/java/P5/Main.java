package P5;


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



    private Connection closeConnection() {
        try {
            getConnection();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }


    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
    }

    private static void testProductAndOvChipkaartDAO(ProductDAOPsql pdao, OVChipkaartDAOPsql odao) {
        System.out.println("\n---------- Test Product and Ovchipkaart DAO -------------");


        System.out.println("\n---- [Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product product : pdao.findAll()) {
            System.out.println(product);
        }

        System.out.println("\n---- [Test] save(); ");

        Product product = new Product( 0001,"Onbeperkt reizen", "Gratis onbeperkt reizen met alle ov", 5.00);

        OVChipkaart ovChipkaart1 = new OVChipkaart(54321, Date.valueOf("2027-01-01"), 1, 500, 1);
        OVChipkaart ovChipkaart2 = new OVChipkaart(54322, Date.valueOf("2027-01-01"), 1, 550, 2);

        odao.save(ovChipkaart1);
        odao.save(ovChipkaart2);

        product.getOvChipkaarts().add(ovChipkaart1);
        product.getOvChipkaarts().add(ovChipkaart2);

        System.out.println("/n ---voor save producten: ");

        for (OVChipkaart ov : odao.findAll()){
            System.out.println(odao);
        }

        pdao.save(product);

        System.out.println("/n ---na save producten: ");

        for (OVChipkaart o : odao.findAll()){
            System.out.println();
        }
    }



    public static void main (String [] args) throws SQLException{
        Main main = new Main();
        OVChipkaartDAOPsql ovChipkaartDAOPsql = new OVChipkaartDAOPsql(connection);

        ProductDAOPsql productDAOPsql = new ProductDAOPsql(connection, ovChipkaartDAOPsql);


        Main.testProductAndOvChipkaartDAO(productDAOPsql,ovChipkaartDAOPsql);
    }
}
