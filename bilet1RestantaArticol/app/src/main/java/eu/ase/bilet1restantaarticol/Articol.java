package eu.ase.bilet1restantaarticol;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Articol implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "titlu")
    private String titlu;

    @ColumnInfo(name = "prima_pagina")
    private String primaPagina;

    @ColumnInfo(name = "ultima_pagina")
    private String ultimaPagina;

    @ColumnInfo(name = "numar_autori")
    private int numarAutori;

    public Articol(long id, String titlu, String primaPagina, String ultimaPagina, int numarAutori) {
        this.id = id;
        this.titlu = titlu;
        this.primaPagina = primaPagina;
        this.ultimaPagina = ultimaPagina;
        this.numarAutori = numarAutori;
    }

    @Ignore
    public Articol(String titlu, String primaPagina, String ultimaPagina, int numarAutori) {
        this.titlu = titlu;
        this.primaPagina = primaPagina;
        this.ultimaPagina = ultimaPagina;
        this.numarAutori = numarAutori;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
