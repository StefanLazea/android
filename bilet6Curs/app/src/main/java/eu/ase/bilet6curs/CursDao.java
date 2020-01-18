package eu.ase.bilet6curs;

import androidx.room.Dao;
import androidx.room.Insert;

//legatura cu baza de date; se va folosi doar de conexiune
//operatii dml
@Dao
public interface CursDao {
    @Insert
    long insert(Curs curs);
}
