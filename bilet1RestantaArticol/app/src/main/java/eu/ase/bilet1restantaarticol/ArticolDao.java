package eu.ase.bilet1restantaarticol;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ArticolDao {
    @Insert
    long insert(Articol articol);
}
