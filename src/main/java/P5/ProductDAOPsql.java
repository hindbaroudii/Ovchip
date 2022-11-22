package P5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAOPsql  odao = null;

    public ProductDAOPsql(Connection conn, OVChipkaartDAOPsql odao) {
        this.conn = conn;
        this.odao = new OVChipkaartDAOPsql(conn);
    }

    @Override
    public boolean save(Product product) {
        try{
            String query = "INSERT INTO product VVALUES (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProductnummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setDouble(4, product.getPrijs());

            pst.execute();

            // add the relation between the product and ovkaart
            addRelations(product);

            return true;

        }catch (SQLException sqlException){
            System.out.println("Error! "+ sqlException.getMessage());
            return false;
        }
    }



    @Override
    public boolean update(Product product) {
        try{
            String query = "UPDATE product SET naam = ? , beschtijving = ? , prijs = ? WHERE product_nummer = ? ";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1 , product.getNaam());
            pst.setString(2, product.getBeschrijving());
            pst.setDouble(3, product.getPrijs());
            pst.setInt(4,product.getProductnummer());
            pst.execute();

            // delete and set the relation
            removeRelations(product);
            addRelations(product);

            return true;

        }catch (SQLException sqlException){
            System.out.println("ERROR !! "+ sqlException.getMessage());
            return false;
        }
    }



    @Override
    public boolean delete(Product product) {
        try{
            //delete the OVproduct from the cards that have this product
            removeRelations(product);

            // delete the product
            String query ="DELETE FROM product WHERE product_nummer = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,product.getProductnummer());
            pst.execute();

            return true;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
            return false;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> products = new ArrayList<>();
        try{
            String query = "SELECT product.prodcut_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product" +
                    "JOIN product ON ov_chipkaart_product.product_nummer = product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                int product_nummer = rs.getInt("product_nummer");
                String product_naam = rs.getString("naam");
                String product_beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product = new Product(product_nummer,product_naam,product_beschrijving,prijs);
                products.add(product);
            }
            return products;

        }catch (SQLException e){
            System.out.println("Error! "+ e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try{
            String query = "SELECT * FROM product";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                int product_nummer = rs.getInt("product_nummer");
                String product_naam = rs.getString("naam");
                String product_beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product = new Product(product_nummer,product_naam,product_beschrijving,prijs);
                products.add(product);
            }
            return products;
        }catch (SQLException e){
            System.out.println("Error! "+ e.getMessage());
            return null;
        }
    }


    private void removeRelations (Product product){
        try{
            String q = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1,product.getProductnummer());
            pst.execute();
        }catch (SQLException e){
            e.getMessage();
        }
    }


    private void addRelations (Product product){
        try{
            String ovquery = "INSERT INTO ov_chipkaart_product VALUES(?,?,?,?,)";
            PreparedStatement pst = conn.prepareStatement(ovquery);

            pst.setInt(2,product.getProductnummer());

            for(OVChipkaart ovChipkaart : product.getOvChipkaarts()){

                pst.setInt(1,ovChipkaart.getKaartnummer());
                pst.setString(3,"GEKOCHT");
                pst.setDate(4, Date.valueOf(LocalDate.now()));

                pst.execute();
            }

        }catch (SQLException e){
            e.getMessage();
        }
    }
}
