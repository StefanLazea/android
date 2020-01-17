package eu.ase.biletjucator;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Jucator implements Parcelable {
    private String nume;
    private int numar;
    private Date dataNastere;
    private String pozitie;

    public Jucator(String nume, int numar, Date dataNastere, String pozitie) {
        this.nume = nume;
        this.numar = numar;
        this.dataNastere = dataNastere;
        this.pozitie = pozitie;
    }

    protected Jucator(Parcel in) {
        this.nume = in.readString();
        try {
            this.dataNastere = new SimpleDateFormat(AddPlayerActivity.DATE_FORMAT,
                    Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        numar = in.readInt();
        pozitie = in.readString();
    }

    public static final Creator<Jucator> CREATOR = new Creator<Jucator>() {
        @Override
        public Jucator createFromParcel(Parcel in) {
            return new Jucator(in);
        }

        @Override
        public Jucator[] newArray(int size) {
            return new Jucator[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "nume='" + nume + '\'' +
                ", numar=" + numar +
                ", dataNastere=" + dataNastere +
                ", pozitie='" + pozitie + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeInt(numar);
        String dateStr = this.dataNastere != null ?
                new SimpleDateFormat(AddPlayerActivity.DATE_FORMAT, Locale.US).format(dataNastere)
                : null;
        dest.writeString(dateStr);
        dest.writeString(pozitie);
    }
}
