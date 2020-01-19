package eu.ase.bilet5examen;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ExamenDao {
    @Insert
    long insert(Examen examen);
}
