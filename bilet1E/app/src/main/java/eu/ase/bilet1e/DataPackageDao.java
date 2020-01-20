package eu.ase.bilet1e;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface DataPackageDao {
    @Insert
    long insert(DataPackage data);
}
