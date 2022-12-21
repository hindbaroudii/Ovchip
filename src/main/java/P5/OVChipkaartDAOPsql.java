package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;
    private ProductDAOPsql pdao;

    public OVChipkaartDAOPsql(Connection conn)  throws SQLException {
        this.conn = conn;
    }

    public void setPdao(ProductDAOPsql pdao) {
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO ov_chipkaart values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
            preparedStatement.setDate(2, ovChipkaart.getGeldigtot());
            preparedStatement.setInt(3, ovChipkaart.getKlasse());
            preparedStatement.setDouble(4, ovChipkaart.getSaldo());
            preparedStatement.setInt(5, ovChipkaart.getReizigerid());

            return preparedStatement.execute();

        } catch(Exception e) {
            return false;
        }
    }


    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer=?, geldig_tot=?, klasse=?, saldo=? WHERE reiziger_id=?");
            preparedStatement.setInt(1,ovChipkaart.getKaartnummer());
            preparedStatement.setDate(2,ovChipkaart.getGeldigtot());
            preparedStatement.setInt(3,ovChipkaart.getKlasse());
            preparedStatement.setDouble(4,ovChipkaart.getSaldo());
            preparedStatement.setInt(5,ovChipkaart.getReizigerid());

            preparedStatement.execute();

            for (Product product : pdao.findByOVChipkaart(ovChipkaart)){
                if (ovChipkaart.getProducts().contains(product)){
                    pdao.update(product);
                }
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?");
            preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
            preparedStatement.execute();

            PreparedStatement prs = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer  = ? ;");
            prs.setInt(1,ovChipkaart.getKaartnummer());

            preparedStatement.execute();

            return true;
        } catch(Exception e) {
            return false;
        }
    }

//    @Override
//    public List<OVChipkaart> findall() {
//        List<OVChipkaart> kaarten = new ArrayList<>();
//        try {
//            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int kaart_nummer = resultSet.getInt("kaart_nummer");
//                Date geldig_tot = resultSet.getDate("geldig_tot");
//                int klasse = resultSet.getInt("klasse");
//                double saldo = resultSet.getDouble("saldo");
//                int reiziger_id = resultSet.getInt("reiziger_id");
//                OVChipkaart o1= new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id);
//                o1.setProducten(pdao.findByOVChipkaart(o1));
//                kaarten.add(o1);
//            }
//            return kaarten;
//        } catch(Exception e) {
//            return null;
//        }
//    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) { //dit is veranderd naar een list omdat een reiziger meerdere kaarten kan hebben
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id=?");
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OVChipkaart> ovChipkaarten = new ArrayList<>();

            int kaartnummer;
            Date geldigheid;
            int klasse;
            double saldo;
            int reizigerid;

            while(resultSet.next()) {
                kaartnummer = resultSet.getInt("kaart_nummer");
                geldigheid = resultSet.getDate("geldig_tot");
                klasse = resultSet.getInt("klasse");
                saldo = resultSet.getDouble("saldo");
                reizigerid = resultSet.getInt("reiziger_id");

                ovChipkaarten.add(new OVChipkaart(kaartnummer,geldigheid,klasse,saldo,reizigerid));
            }
            return ovChipkaarten;
        } catch (Exception e) {
            return null;
        }
    }
}
