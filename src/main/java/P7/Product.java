package P7;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private int product_nummer;

    @Column
    private String naam;
    private String beschrijving;
    private double prijs;

    public Product() {}

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    @ManyToMany(mappedBy = "productList", targetEntity = OVChipkaart.class)
    private List<OVChipkaart> ovChipList;

    public int getProductNummer() {
        return product_nummer;
    }

    public void setProductNummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipList() {
        return ovChipList;
    }

    public void setOvChipList(List<OVChipkaart> ovChipList) {
        this.ovChipList = ovChipList;
    }

    public String toString() {
        return "\nProducts { " +
                product_nummer + ", " +
                naam + ", " +
                beschrijving + ", " +
                prijs + "}";
    }

}