package eu.ase.biletjucator;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


//legatura cu baza de date; se va folosi doar de conexiune
//operatii dml
@Dao
public interface JucatorDao {

    @Insert
    long insert(Jucator jucator);

    @Query("SELECT * FROM jucatori")
    List<Jucator> getAll();


}
