package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private final Connection connection;
    private final AdresDAO adao;
    private final OVChipkaartDAO odao;

    public ReizigerDAOPsql(Connection connection) throws SQLException {
        this.connection = connection;
        this.adao = new AdresDAOPsql(connection);
        this.odao = new OVChipkaartDAOPsql(connection);
    }

    @Override
    public boolean save(Reiziger reiziger){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reiziger values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboortedatum());

            preparedStatement.execute();


            for (OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()) {
                odao.save(ovChipkaart);
            }

            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=? ");
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboortedatum());
            preparedStatement.setInt(5, reiziger.getId());
            preparedStatement.execute();

            //update het adres van de reiziger
            adao.update(reiziger.getAdres());


            //loopt door alle opgehaalde kaarten en vergelijkt ze met de kaarten van het reiziger object
            for (OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()) {
                    odao.update(ovChipkaart); //als de kaart bestaat in het reiziger object wordt deze geupdate
            }

            return true;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            adao.delete(reiziger.getAdres());
            for (OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()){
                odao.delete(ovChipkaart);
            }
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?;");
            preparedStatement.setInt(1,reiziger.getId());
            return preparedStatement.execute();

        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            String voorletters = null;
            String tussenvoegsel = null;
            String achternaam = null;
            Date geboortedatum = null;
            Reiziger reiziger;

            while (resultSet.next()){
                voorletters = resultSet.getString("voorletters");
                tussenvoegsel = resultSet.getString("tussenvoegsel");
                achternaam = resultSet.getString("achternaam");
                geboortedatum = resultSet.getDate("geboortedatum");
            }
            reiziger = new Reiziger(id,voorletters,tussenvoegsel,achternaam,geboortedatum);
            return reiziger;

        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            List<Reiziger> reizigers = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum=?");
            preparedStatement.setDate(1, Date.valueOf(datum));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reizigerid = resultSet.getInt("reiziger_id");
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date geboortedatum = resultSet.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(reizigerid, voorletters, tussenvoegsel, achternaam, geboortedatum);
                reizigers.add(reiziger);
            }
            return reizigers;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {

        try {
            List<Reiziger> reizigers = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reiziger;");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("reiziger_id");
                int reizigerid = Integer.parseInt(id);
                String voorletters = resultSet.getString("voorletters");
                String tussenvoegsel = resultSet.getString("tussenvoegsel");
                String achternaam = resultSet.getString("achternaam");
                Date gbdatum = resultSet.getDate("geboortedatum");
                Reiziger reiziger = new Reiziger(reizigerid,voorletters,tussenvoegsel,achternaam,gbdatum);

                reizigers.add(reiziger);
            }
            return reizigers;

        } catch (SQLException e) {
            return null;
        }
    }
}