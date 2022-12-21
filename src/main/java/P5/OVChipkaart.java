package P5;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldigtot;
    private int klasse;
    private double saldo;
    private int reizigerid;

    private List<Product> products;

    public OVChipkaart(int kaartnummer, Date geldigtot, int klasse, double saldo, int reiziger_id) {
        this.kaartnummer = kaartnummer;
        this.geldigtot = geldigtot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerid = reiziger_id;
    }

    public OVChipkaart(int kaartnummer, Date geldigtot, int klasse, double saldo, int reizigerid, List<Product> products) {
        this.kaartnummer = kaartnummer;
        this.geldigtot = geldigtot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerid = reizigerid;
        this.products = products;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public Date getGeldigtot() {
        return geldigtot;
    }

    public void setGeldigtot(Date geldigtot) {
        this.geldigtot = geldigtot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getReizigerid() {
        return reizigerid;
    }

    public void setReizigerid(int reizigerid) {
        this.reizigerid = reizigerid;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", geldigtot=" + geldigtot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reizigerid=" + reizigerid +
                '}';
    }
}