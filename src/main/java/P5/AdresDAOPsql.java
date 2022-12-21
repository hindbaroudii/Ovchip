package P5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn = Main.getConnection();

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO adres values(?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReizigerid());

            return preparedStatement.execute();
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE adres SET  postcode=?, huisnummer=?, straat=?,  woonplaats=?, reiziger_id = ? WHERE adres_id=?;");
            preparedStatement.setString(1, adres.getPostcode());
            preparedStatement.setString(2, adres.getHuisnummer());
            preparedStatement.setString(3, adres.getStraat());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getReizigerid());
            preparedStatement.setInt(6, adres.getId());

            return preparedStatement.execute();

        } catch(Exception e) {
            return false;
        }
    }


    public boolean delete(Adres adres) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM adres WHERE adres_id = ?;");
            preparedStatement.setInt(1, adres.getId());
            return preparedStatement.execute();
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id=?;");
            preparedStatement.setInt(1, reiziger.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            int adresid = 0;
            String postcode = null;
            String huisnummer = null;
            String straat = null;
            String woonplaats = null;
            int reizigerid = 0;
            Adres adres;

            while (resultSet.next()){
                adresid = resultSet.getInt("adres_id");
                postcode = resultSet.getString("postcode");
                huisnummer = resultSet.getString("huisnummer");
                straat = resultSet.getString("straat");
                woonplaats = resultSet.getString("woonplaats");
                reizigerid = resultSet.getInt("reiziger_id");
            }
            adres = new Adres(adresid,postcode,huisnummer,straat,woonplaats,reizigerid);

            return adres;

        } catch(Exception e) {
            return null;
        }
    }


    @Override
    public List<Adres> findAll() {
        try {
            List<Adres> adressen = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM adres");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int adresid = resultSet.getInt("adres_id");
                String postcode = resultSet.getString("postcode");
                String huisnummer = resultSet.getString("huisnummer");
                String straat = resultSet.getString("straat");
                String woonplaats = resultSet.getString("woonplaats");
                int reizigerid = resultSet.getInt("reiziger_id");
                Adres adres = new Adres(adresid,postcode,huisnummer,straat,woonplaats,reizigerid);
                adressen.add(adres);
            }
            return adressen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}