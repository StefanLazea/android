package eu.ase.bilet6curs;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Curs.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {

    public static final String DB_NAME = "cursuri";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(
                            context,
                            DatabaseManager.class,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
            return databaseManager;
        }
        return databaseManager;
    }

    //plasarea conexiunii catre Dao
    public abstract CursDao getCursDao();
}
