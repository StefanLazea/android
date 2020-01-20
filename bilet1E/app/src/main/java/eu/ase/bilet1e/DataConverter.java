package eu.ase.bilet1e;

import androidx.room.TypeConverter;

import java.util.Date;

public class DataConverter {
    @TypeConverter
    public Date fromTimeStamp(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public Long fromDate(Date date) {
        return date != null ? date.getTime() : null;
    }
}
