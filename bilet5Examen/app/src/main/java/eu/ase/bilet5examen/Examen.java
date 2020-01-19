package eu.ase.bilet5examen;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Examen implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="denumire_materie")
    private String denumireMaterie;
    @ColumnInfo(name = "numar_studenti")
    private int numarStudenti;
    @ColumnInfo(name = "sala")
    private String sala;
    @ColumnInfo(name = "supraveghetor")
    private String supraveghetor;

    public Examen(long id, String denumireMaterie, int numarStudenti, String sala, String supraveghetor) {
        this.id = id;
        this.denumireMaterie = denumireMaterie;
        this.numarStudenti = numarStudenti;
        this.sala = sala;
        this.supraveghetor = supraveghetor;
    }

    protected Examen(Parcel in) {
        id = in.readLong();
        denumireMaterie = in.readString();
        numarStudenti = in.readInt();
        sala = in.readString();
        supraveghetor = in.readString();
    }

    public static final Creator<Examen> CREATOR = new Creator<Examen>() {
        @Override
        public Examen createFromParcel(Parcel in) {
            return new Examen(in);
        }

        @Override
        public Examen[] newArray(int size) {
            return new Examen[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDenumireMaterie() {
        return denumireMaterie;
    }

    public void setDenumireMaterie(String denumireMaterie) {
        this.denumireMaterie = denumireMaterie;
    }

    public int getNumarStudenti() {
        return numarStudenti;
    }

    public void setNumarStudenti(int numarStudenti) {
        this.numarStudenti = numarStudenti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getSupraveghetor() {
        return supraveghetor;
    }

    public void setSupraveghetor(String supraveghetor) {
        this.supraveghetor = supraveghetor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.denumireMaterie);
        dest.writeInt(this.numarStudenti);
        dest.writeString(this.sala);
        dest.writeString(this.supraveghetor);
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", denumireMaterie='" + denumireMaterie + '\'' +
                ", numarStudenti=" + numarStudenti +
                ", sala='" + sala + '\'' +
                ", supraveghetor='" + supraveghetor + '\'' +
                '}';
    }
}
