package eu.ase.bilet62018;


import androidx.room.TypeConverter;

import java.util.Date;

class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date != null ? date.getTime() : null;
    }
}
