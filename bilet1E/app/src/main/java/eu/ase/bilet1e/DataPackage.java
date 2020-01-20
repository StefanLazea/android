package eu.ase.bilet1e;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "pachete")
@TypeConverters({DataConverter.class})
public class DataPackage implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "package_id")
    private long packageId;
    @ColumnInfo(name = "packge_name")
    private String packageType;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "timestamp")
    private Date timestamp;

    public DataPackage(long packageId, String packageType, double latitude, double longitude, Date timestamp) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    protected DataPackage(Parcel in) {
        packageId = in.readLong();
        packageType = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        try {
            timestamp = new SimpleDateFormat(AddPackageActivity.DATE_FORMAT, Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<DataPackage> CREATOR = new Creator<DataPackage>() {
        @Override
        public DataPackage createFromParcel(Parcel in) {
            return new DataPackage(in);
        }

        @Override
        public DataPackage[] newArray(int size) {
            return new DataPackage[size];
        }
    };

    @Override
    public String toString() {
        return "DataPackage{" +
                "packageId=" + packageId +
                ", packageType='" + packageType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.packageId);
        dest.writeString(this.packageType);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        String date = new SimpleDateFormat(AddPackageActivity.DATE_FORMAT, Locale.US).format(this.timestamp);
        dest.writeString(date);
    }
}
