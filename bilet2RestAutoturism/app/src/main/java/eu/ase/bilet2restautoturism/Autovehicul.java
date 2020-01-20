package eu.ase.bilet2restautoturism;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "autovehicule")
@TypeConverters({DateConverter.class})
public class Autovehicul implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    long id;
    @ColumnInfo(name = "numar_auto")
    private String numarAuto;
    @ColumnInfo(name = "data_inregistrare")
    private Date dataInregistrare;
    @ColumnInfo(name = "id_loc_parcare")
    private int idLocParcare;
    @ColumnInfo(name = "plata_efectuata")
    private boolean plataEfectuata;

    public Autovehicul(long id, String numarAuto, Date dataInregistrare, int idLocParcare, boolean plataEfectuata) {
        this.id = id;
        this.numarAuto = numarAuto;
        this.dataInregistrare = dataInregistrare;
        this.idLocParcare = idLocParcare;
        this.plataEfectuata = plataEfectuata;
    }

    @Ignore
    public Autovehicul(String numarAuto, Date dataInregistrare, int idLocParcare, boolean plataEfectuata) {
        this.numarAuto = numarAuto;
        this.dataInregistrare = dataInregistrare;
        this.idLocParcare = idLocParcare;
        this.plataEfectuata = plataEfectuata;
    }

    protected Autovehicul(Parcel in) {
        numarAuto = in.readString();
        try {
            this.dataInregistrare = new SimpleDateFormat(AddAutoActivity.DATE_FORMAT, Locale.US)
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        idLocParcare = in.readInt();
        plataEfectuata = in.readBoolean();
    }

    public static final Creator<Autovehicul> CREATOR = new Creator<Autovehicul>() {
        @Override
        public Autovehicul createFromParcel(Parcel in) {
            return new Autovehicul(in);
        }

        @Override
        public Autovehicul[] newArray(int size) {
            return new Autovehicul[size];
        }
    };

    @Override
    public String toString() {
        return "Autovehicul{" +
                "numarAuto='" + numarAuto + '\'' +
                ", dataInregistrare=" + dataInregistrare +
                ", idLocParcare=" + idLocParcare +
                ", plataEfectuata=" + plataEfectuata +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumarAuto() {
        return numarAuto;
    }

    public void setNumarAuto(String numarAuto) {
        this.numarAuto = numarAuto;
    }

    public Date getDataInregistrare() {
        return dataInregistrare;
    }

    public void setDataInregistrare(Date dataInregistrare) {
        this.dataInregistrare = dataInregistrare;
    }

    public int getIdLocParcare() {
        return idLocParcare;
    }

    public void setIdLocParcare(int idLocParcare) {
        this.idLocParcare = idLocParcare;
    }

    public boolean getPlataEfectuata() {
        return plataEfectuata;
    }

    public void setPlataEfectuata(boolean plataEfectuata) {
        this.plataEfectuata = plataEfectuata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.numarAuto);
        String dateStr = this.dataInregistrare != null ?
                new SimpleDateFormat(AddAutoActivity.DATE_FORMAT, Locale.US).format(this.dataInregistrare) : null;
        dest.writeString(dateStr);
        dest.writeInt(this.idLocParcare);
        dest.writeBoolean(this.plataEfectuata);


    }
}
