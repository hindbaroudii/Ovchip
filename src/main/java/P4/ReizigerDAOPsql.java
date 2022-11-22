package P4;

import P2.Reiziger;
import P2.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;
    private Statement st = null;
    private ResultSet rs = null;
    private List<Reiziger> reizigers = new ArrayList<>();

    public ReizigerDAOPsql(Connection conn) {
        this.conn =  conn;
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        if (!reizigers.contains(reiziger)) {

            String query = "INSERT INTO reiziger(reiziger_id, voorletters,tussenvoegsel,achternaam,geboortedatum) " +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());

            return pst.execute();
        }else {
            System.out.println("\nReiziger met id: "+ reiziger.getId() + "bestaat al");
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        if (reizigers.contains(reiziger)) {
            st = conn.createStatement();
            String query = "UPDATE reiziger " +
                    "SET voorletters = " + reiziger.getVoorletters() +
                    ",tussenvoegsel = " + reiziger.getTussenvoegsel() +
                    ", achternaam = " + reiziger.getAchternaam() +
                    ",geboortedatum = " + reiziger.getGeboortedatum() +
                    " WHERE reiziger_id = " + reiziger.getId();

            rs = st.executeQuery(query);

            System.out.println(reiziger);

            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        if(reizigers.contains(reiziger)){

            st = conn.createStatement();
            String query = "DELETE FROM reiziger WHERE reiziger_id = ? ";
            PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,String.valueOf(reiziger.getId()));
            rs = st.executeQuery(query);

            reizigers.remove(reiziger);

            System.out.println(reiziger);

            return true;

        }
        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        for(Reiziger reiziger : reizigers){
            if(reiziger.getId() == id){
                String query = "SELECT * FROM reiziger WHERE reiziger_id = ? ";

                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1,reiziger.getId());

                rs = st.executeQuery(query);
                rs.next();

                int reiziger_id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date gbdatum = rs.getDate("geboortedatum");

                return new Reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,gbdatum);
            }
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        for(Reiziger reiziger : reizigers){
            if(reiziger.getGeboortedatum().toString().equals(datum)){
                st = conn.createStatement();
                String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
                PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1,String.valueOf(reiziger.getGeboortedatum()));
                rs = st.executeQuery(query);

                System.out.println(reiziger);
            }
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        st = conn.createStatement();
        String query = "SELECT * FROM reiziger";
        rs = st.executeQuery(query);

        while (rs.next()){
            Reiziger reiziger = new Reiziger();
            reiziger.setId(Integer.parseInt(rs.getString("reiziger_id")));
            reiziger.setVoorletters(rs.getString("voorletters"));
            reiziger.setTussenvoegsel(rs.getString("tussenvoegsel"));
            reiziger.setAchternaam(rs.getString("achternaam"));
            reiziger.setGeboortedatum(Date.valueOf(rs.getString("geboortedatum")));
            reizigers.add(reiziger);
        }

        return reizigers;
    }
}
