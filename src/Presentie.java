//import java.sql.*;
//
//public class Presentie {
//    private final String url = "jdbc:postgresql://localhost/ovchip";
//    private final String user = "postgres";
//    private final String password = "zxcv";
//
//    public Connection connect(){
//        Connection con = null;
//        Statement st = null;
//        ResultSet rs = null;
//        try{
//            con = DriverManager.getConnection( url, user, password);
//            System.out.println("Connected to the PostgreSQL server successfully.");
//
//            st =  con.createStatement();
//            rs = st.executeQuery("SELECT * FROM reiziger ORDER BY reiziger_id ASC");
//            System.out.println("Alle reizigers:");
//            while (rs.next()){
//                System.out.println("#" + rs.getString("reiziger_id") + ": "
//                        + rs.getString("voorletters") +". "
//                        + rs.getString("tussenvoegsel") + " "
//                        + rs.getString("achternaam") + " ("
//                        + rs.getString("geboortedatum")+")");
//            }
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//        return con;
//    }
//
//    public static void main (String [] args){
//        Main main = new Main();
//        main.connect();
//    }
//}
