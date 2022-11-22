package P5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn = Main.getConnection();
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try{
            String query = "INSERT INTO adres(adres_id, postcode, huisnummer, straat,  woonplaats, reiziger_id)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1,adres.getID());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReizigerID());

            return ps.execute();

        }catch (SQLException sqlException){
            System.out.println("Error! " + sqlException.getMessage());
            return false;
        }
    }


    @Override
    public boolean update(Adres adres) {
        try{
            String query = "UPDATE adres SET postcode = ?, huisnummer = ? , straat = ?, " +
                    "woonplaats = ? , reiziger_id = ? WHERE adres_id = ?; ";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,adres.getPostcode());
            ps.setString(2,adres.getHuisnummer());
            ps.setString(3,adres.getStraat());
            ps.setString(4,adres.getWoonplaats());
            ps.setInt(5,adres.getReizigerID());
            ps.setInt(6,adres.getID());

            return ps.execute();

        }catch (SQLException sqlException){

            System.out.println("Error! "+ sqlException.getMessage());
            return false;
        }
    }


    @Override
    public boolean delete(Adres adres) {
        try{
            String query = "DELETE FROM adres WHERE adres_id = ? ;";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1,adres.getID());

            return ps.execute();

        }catch (SQLException sqlException){
            System.out.println("ERROR! "+ sqlException.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ? ";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, reiziger.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");

                return new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id);
            }
        }catch (SQLException sqlException) {
            System.out.println("Error !  "+ sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try{
            List<Adres> adresList = new ArrayList<>();
            String query = "SELECT * FROM adres; " ;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");

                Adres adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id);
                adresList.add(adres);

            }
            return adresList;

        }catch (SQLException sqlException){
            System.out.println("ERROR!" + sqlException.getMessage());
            return null;
        }
    }
}
