package eu.ase.bilet4rezervare;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rezervare implements Parcelable {
    private long idRezervare;
    private String numeClient;
    private String tipCamera;
    private int durataSejur;
    private float sumaPlata;
    private Date dataCazare;

    public Rezervare(long idRezervare, String numeClient, String tipCamera, int durataSejur, float sumaPlata, Date dataCazare) {
        this.idRezervare = idRezervare;
        this.numeClient = numeClient;
        this.tipCamera = tipCamera;
        this.durataSejur = durataSejur;
        this.sumaPlata = sumaPlata;
        this.dataCazare = dataCazare;
    }

    protected Rezervare(Parcel in) {
        idRezervare = in.readLong();
        numeClient = in.readString();
        tipCamera = in.readString();
        durataSejur = in.readInt();
        sumaPlata = in.readFloat();
        try {
            this.dataCazare = new SimpleDateFormat(AddReservationActivity.DATE_FORMAT, Locale.US)
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static final Creator<Rezervare> CREATOR = new Creator<Rezervare>() {
        @Override
        public Rezervare createFromParcel(Parcel in) {
            return new Rezervare(in);
        }

        @Override
        public Rezervare[] newArray(int size) {
            return new Rezervare[size];
        }
    };

    public long getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(long idRezervare) {
        this.idRezervare = idRezervare;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public int getDurataSejur() {
        return durataSejur;
    }

    public void setDurataSejur(int durataSejur) {
        this.durataSejur = durataSejur;
    }

    public float getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(float sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public Date getDataCazare() {
        return dataCazare;
    }

    public void setDataCazare(Date dataCazare) {
        this.dataCazare = dataCazare;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.idRezervare);
        dest.writeString(this.numeClient);
        dest.writeString(this.tipCamera);
        dest.writeInt(this.durataSejur);
        dest.writeFloat(this.sumaPlata);

        String dateStr = this.dataCazare != null ?
                new SimpleDateFormat(AddReservationActivity.DATE_FORMAT, Locale.US)
                        .format(this.dataCazare) : null;
        dest.writeString(dateStr);

    }


}
