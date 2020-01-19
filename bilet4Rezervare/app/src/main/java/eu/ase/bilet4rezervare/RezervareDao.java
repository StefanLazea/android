package eu.ase.bilet4rezervare;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface RezervareDao {
    @Insert
    long insert(Rezervare rez);
}
