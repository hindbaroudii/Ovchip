package P5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAOPsql odao = null;

    public ProductDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
        this.odao = new OVChipkaartDAOPsql(conn);
    }

    @Override
    public boolean save(Product product) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO product values(?, ?, ?, ?)");
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeschrijving());
            preparedStatement.setDouble(4, product.getPrijs());

            preparedStatement.execute();

            PreparedStatement prs = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?,?,?,?)");

            System.out.println(product.getOvChipkaarten());

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                prs.setInt(1, ovChipkaart.getKaartnummer());
                prs.setInt(2, product.getProduct_nummer());
                prs.setString(3, "gekocht");
                prs.setDate(4, Date.valueOf(LocalDate.now()));
                prs.execute();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE product SET product_nummer=?, naam=?, beschrijving=? , prijs = ? WHERE product_nummer=? ");
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeschrijving());
            preparedStatement.setDouble(4, product.getPrijs());
            preparedStatement.setInt(5, product.getProduct_nummer());

            preparedStatement.execute();

            PreparedStatement prs = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ? ;");
            prs.setInt(1, product.getProduct_nummer());
            prs.execute();

            PreparedStatement prs1 = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES(?,?,?,?)");

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                prs1.setInt(1, ovChipkaart.getKaartnummer());
                prs1.setInt(2, product.getProduct_nummer());
                prs1.setString(3, "sold");
                prs1.setDate(4, Date.valueOf(LocalDate.now()));
                prs1.execute();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(Product product) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ? ");
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.execute();

            PreparedStatement prs = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            prs.setInt(1, product.getProduct_nummer());
            return prs.execute();

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Product> findall() {
        try {
            List<Product> products = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM product;");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int productnummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                Double prijs = rs.getDouble("prijs");
                Product product = new Product(productnummer, naam, beschrijving,prijs);

                products.add(product);
            }
            return products;

        } catch (Exception e) {
            return null;
        }
    }



    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT p.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product ocp JOIN product p on ocp.product_nummer = p.product_nummer WHERE ocp.kaart_nummer = ?");
            preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            while (rs.next()) {
                int nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product = new Product(nummer, naam, beschrijving, prijs);

                products.add(product);
            }
            return products;
        } catch (Exception e) {
            return null;
        }
    }
}
