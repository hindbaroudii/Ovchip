package P4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        // Maak een nieuwe reiziger aan en persisteer deze in de database
//        String gbdatum = "1981-03-14";
//        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
//        System.out.println("sietske is verwijderd!");
//        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
//        rdao.save(sietske);
//        reizigers = rdao.findAll();
//        System.out.println(reizigers.size() + " reizigers\n");

    }



    public static void main (String [] args) throws SQLException{
        Main main = new Main();


    }
}
