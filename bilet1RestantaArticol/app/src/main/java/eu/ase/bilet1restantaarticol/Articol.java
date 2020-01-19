package eu.ase.bilet1restantaarticol;

import android.os.Parcel;
import android.os.Parcelable;

public class Articol implements Parcelable {
    private String titlu;
    private String primaPagina;
    private String ultimaPagina;
    private int numarAutori;

    public Articol(String titlu, String primaPagina, String ultimaPagina, int numarAutori) {
        this.titlu = titlu;
        this.primaPagina = primaPagina;
        this.ultimaPagina = ultimaPagina;
        this.numarAutori = numarAutori;
    }

    protected Articol(Parcel in) {
        titlu = in.readString();
        primaPagina = in.readString();
        ultimaPagina = in.readString();
        numarAutori = in.readInt();
    }

    public static final Creator<Articol> CREATOR = new Creator<Articol>() {
        @Override
        public Articol createFromParcel(Parcel in) {
            return new Articol(in);
        }

        @Override
        public Articol[] newArray(int size) {
            return new Articol[size];
        }
    };

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getPrimaPagina() {
        return primaPagina;
    }

    public void setPrimaPagina(String primaPagina) {
        this.primaPagina = primaPagina;
    }

    public String getUltimaPagina() {
        return ultimaPagina;
    }

    public void setUltimaPagina(String ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    public int getNumarAutori() {
        return numarAutori;
    }

    public void setNumarAutori(int numarAutori) {
        this.numarAutori = numarAutori;
    }

    @Override
    public String toString() {
        return "Articol{" +
                "titlu='" + titlu + '\'' +
                ", primaPagina='" + primaPagina + '\'' +
                ", ultimaPagina='" + ultimaPagina + '\'' +
                ", numarAutori=" + numarAutori +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titlu);
        dest.writeString(this.primaPagina);
        dest.writeString(this.ultimaPagina);
        dest.writeInt(this.numarAutori);

    }
}
