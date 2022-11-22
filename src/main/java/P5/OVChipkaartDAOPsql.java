package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            String query = "INSERT INTO ov_chipkaart(kaart_nummer,geldig_tot,klasse,saldo,reiziger_id)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, ovChipkaart.getKaartnummer());
            ps.setDate(2, ovChipkaart.getGeldigTot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger_id());

            return ps.execute();

        }catch (SQLException sqlException){
            System.out.println("Error! " + sqlException.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try{
            String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ? , klasse = ?, " +
                    "saldo = ? WHERE reiziger_id = ?; ";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, ovChipkaart.getKaartnummer());
            ps.setDate(2, ovChipkaart.getGeldigTot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setDouble(4,ovChipkaart.getSaldo());
            ps.setInt(5,ovChipkaart.getReiziger_id());

            return ps.execute();

        }catch (SQLException sqlException){

            System.out.println("Error! "+ sqlException.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try{
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ? ;";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1,ovChipkaart.getKaartnummer());

            return ps.execute();

        }catch (SQLException sqlException){
            System.out.println("ERROR! "+ sqlException.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ? ";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, reiziger.getId());

            ResultSet rs = ps.executeQuery();

            List<OVChipkaart> ovChipkaarts = new ArrayList<>();

            while (rs.next()) {
                int kaartnummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");

                ovChipkaarts.add(new OVChipkaart(kaartnummer,geldigTot,klasse,saldo,reiziger_id));

            }
            return ovChipkaarts;

        }catch (SQLException sqlException) {
            System.out.println("Error !  "+ sqlException.getMessage());
        }
        return null;
    }

}

