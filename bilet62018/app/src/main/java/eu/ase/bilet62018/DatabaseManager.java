package eu.ase.bilet62018;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HomeExchange.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {
    public static final String DB_NAME = "sharing";
    public static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(
                            context,
                            DatabaseManager.class,
                            DB_NAME
                    ).fallbackToDestructiveMigration()
                            .build();
                }
            }
            return databaseManager;
        }
        return databaseManager;
    }

    public abstract HomeExchangeDao getHomeExchangeDao();
}
