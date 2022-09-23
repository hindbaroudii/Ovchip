import model.*;

import java.sql.*;
import java.util.List;

public class Main {

    private static Connection connection;


    private Connection getConnection() {
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

        // Maak een nieuwe reiziger aan en persisteer deze in de database
//        String gbdatum = "1981-03-14";
//        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
//        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
//        rdao.save(sietske);
//        reizigers = rdao.findAll();
//        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        List<Adres> adresList = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adresList) {
            System.out.println(a);
        }
        System.out.println();

    }

    public static void main (String [] args) throws SQLException{
        Main main = new Main();
        ReizigerDAO rDao = new ReizigerDAOPsql(connection);
        AdresDAO aDao = new AdresDAOPsql(connection);
        main.getConnection();
        Main.testReizigerDAO(rDao);
        Main.testAdresDAO(aDao);
    }

}
