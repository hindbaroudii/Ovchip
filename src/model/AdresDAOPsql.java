package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;
    private ReizigerDAO reizigerDAO;
    private Statement st = null;
    private ResultSet rs = null;

    public AdresDAOPsql(Connection conn) {
        try {
            final String url = "jdbc:postgresql://localhost/ovchip";
            final String user = "postgres";
            final String password = "zxcv";
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        if (reizigerDAO.findById(adres.getReizigerID()) == null) {
            return false;
        } else {
            String query = "INSERT INTO adres(adres_id, postcode, huisnummer, straat,  woonplaats, reiziger_id)" +
                    "VALUES(?,?,?,?,?,?)";
            st = connection.createStatement();
            rs = st.executeQuery(query);

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(adres.getID()));
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setString(6, String.valueOf(adres.getReizigerID()));

            return true;
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        if (reizigerDAO.findById(adres.getReizigerID()) != null) {
            String query = "UPDATE adres " +
                    "SET adres_id = " + adres.getID() +
                    ",postcode = " + adres.getPostcode() +
                    ", huisnummer = " + adres.getHuisnummer() +
                    ",straat = " + adres.getStraat() +
                    ",woonplaats = " + adres.getWoonplaats() +
                    " WHERE reiziger_id = " + adres.getReizigerID();
            st = connection.createStatement();
            rs = st.executeQuery(query);

            return true;

        } else {
            return false;
        }
    }


    @Override
    public boolean delete(Adres adres) throws SQLException {
        if (reizigerDAO.findById(adres.getReizigerID()) != null) {

            String query = "DELETE FROM adres " +
                    " WHERE reiziger_id = " + adres.getReizigerID();

            st = connection.createStatement();
            rs = st.executeQuery(query);

            return true;

        } else {
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        for (Reiziger r : reizigerDAO.findAll()) {
            if (id == r.getId()) {
                String query = "SELECT * FROM adres WHERE reizger_id = " + id;
                st = connection.createStatement();
                rs = st.executeQuery(query);

            }
        }
        return findByReiziger(reiziger);
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adresList = new ArrayList<>();

        st = connection.createStatement();
        String query = "SELECT * FROM adres";
        rs = st.executeQuery(query);

        while (rs.next()) {
            Adres adres = new Adres();
            adres.setID(Integer.parseInt(rs.getString("adres_id")));
            adres.setPostcode(rs.getString("postcode"));
            adres.setHuisnummer(rs.getString("huisnummer"));
            adres.setStraat(rs.getString("straat"));
            adres.setWoonplaats(rs.getString("woonplaats"));
            adres.setReizigerID(Integer.parseInt(rs.getString("reiziger_id")));

            adresList.add(adres);

        }
        return adresList;

    }
}
