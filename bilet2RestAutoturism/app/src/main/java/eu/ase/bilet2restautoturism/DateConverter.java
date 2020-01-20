package eu.ase.bilet2restautoturism;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static long fromDate(Date date) {
        return date != null ? date.getTime() : null;
    }
}
