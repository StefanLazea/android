package eu.ase.bilet62018;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface HomeExchangeDao {

    @Insert
    long insert(HomeExchange home);
}
