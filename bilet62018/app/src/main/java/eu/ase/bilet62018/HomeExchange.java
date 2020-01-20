package eu.ase.bilet62018;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeExchange implements Parcelable {
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

    private HomeExchange(Parcel in) {
        adresa = in.readString();
        numarCamere = in.readInt();
        suprafata = in.readFloat();
        try {
            this.data = new SimpleDateFormat(AddHomeActivity.DATE_FORMAT,
                    Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tipLocuinta = in.readString();
    }

    public static final Creator<HomeExchange> CREATOR = new Creator<HomeExchange>() {
        @Override
        public HomeExchange createFromParcel(Parcel in) {
            return new HomeExchange(in);
        }

        @Override
        public HomeExchange[] newArray(int size) {
            return new HomeExchange[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adresa);
        dest.writeInt(this.numarCamere);
        dest.writeFloat(this.suprafata);
        String dateStr = this.data != null ? new SimpleDateFormat(AddHomeActivity.DATE_FORMAT,
                Locale.US).format(this.data) : null;
        dest.writeString(dateStr);
        dest.writeString(this.tipLocuinta);

    }
}
