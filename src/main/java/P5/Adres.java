package P5;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerid;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reizigerid) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerid = reizigerid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReizigerid() {
        return reizigerid;
    }

    public void setReizigerid(int reizigerid) {
        this.reizigerid = reizigerid;
    }

    @Override
    public String toString() {
        return "Adres {" +
                "id: " + id +
                ", postcode: '" + postcode + '\'' +
                ", huisnummer: '" + huisnummer + '\'' +
                ", straat: '" + straat + '\'' +
                ", woonplaats: '" + woonplaats + '\'' +
                ", reizigerid: " + reizigerid +
                '}';
    }
}
