package eu.ase.bilet62018;

import java.util.Date;

public class HomeExchange {
    private String adresa;
    private int numarCamere;
    private float suprafata;
    private Date data;
    private String tipLocuinta;

    public HomeExchange(String adresa, int numarCamere, float suprafata, Date data, String tipLocuinta) {
        this.adresa = adresa;
        this.numarCamere = numarCamere;
        this.suprafata = suprafata;
        this.data = data;
        this.tipLocuinta = tipLocuinta;
    }

    @Override
    public String toString() {
        return "HomeExchange{" +
                "adresa='" + adresa + '\'' +
                ", numarCamere=" + numarCamere +
                ", suprafata=" + suprafata +
                ", data=" + data +
                ", tipLocuinta='" + tipLocuinta + '\'' +
                '}';
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNumarCamere() {
        return numarCamere;
    }

    public void setNumarCamere(int numarCamere) {
        this.numarCamere = numarCamere;
    }

    public float getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(float suprafata) {
        this.suprafata = suprafata;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipLocuinta() {
        return tipLocuinta;
    }

    public void setTipLocuinta(String tipLocuinta) {
        this.tipLocuinta = tipLocuinta;
    }
}
