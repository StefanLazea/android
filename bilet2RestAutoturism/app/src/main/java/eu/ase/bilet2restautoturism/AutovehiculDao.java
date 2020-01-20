package eu.ase.bilet2restautoturism;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface AutovehiculDao {

    @Insert
    long insert(Autovehicul auto);
}
