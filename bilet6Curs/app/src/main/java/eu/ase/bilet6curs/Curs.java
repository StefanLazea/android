package eu.ase.bilet6curs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Curs implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_curs")
    private long idCurs;
    @ColumnInfo(name = "denumire")
    private String denumire;
    @ColumnInfo(name = "numar_participanti")
    private int numarParticipanti;
    @ColumnInfo(name = "sala")
    private String sala;
    @ColumnInfo(name = "profesor_titular")
    private String profesorTitular;

    public Curs(long idCurs, String denumire, int numarParticipanti, String sala, String profesorTitular) {
        this.idCurs = idCurs;
        this.denumire = denumire;
        this.numarParticipanti = numarParticipanti;
        this.sala = sala;
        this.profesorTitular = profesorTitular;
    }

    protected Curs(Parcel in) {
        idCurs = in.readLong();
        denumire = in.readString();
        numarParticipanti = in.readInt();
        sala = in.readString();
        profesorTitular = in.readString();
    }

    public static final Creator<Curs> CREATOR = new Creator<Curs>() {
        @Override
        public Curs createFromParcel(Parcel in) {
            return new Curs(in);
        }

        @Override
        public Curs[] newArray(int size) {
            return new Curs[size];
        }
    };

    public long getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(long idCurs) {
        this.idCurs = idCurs;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarParticipanti() {
        return numarParticipanti;
    }

    public void setNumarParticipanti(int numarParticipanti) {
        this.numarParticipanti = numarParticipanti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getProfesorTitular() {
        return profesorTitular;
    }

    public void setProfesorTitular(String profesorTitular) {
        this.profesorTitular = profesorTitular;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Curs{" +
                "idCurs=" + idCurs +
                ", denumire='" + denumire + '\'' +
                ", numarParticipanti=" + numarParticipanti +
                ", sala='" + sala + '\'' +
                ", profesorTitular='" + profesorTitular + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idCurs);
        dest.writeString(this.denumire);
        dest.writeInt(this.numarParticipanti);
        dest.writeString(this.sala);
        dest.writeString(this.profesorTitular);
    }
}
