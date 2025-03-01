package br.com.matotvron.tccgymmanagementapp.background.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.models.Gym;

@Dao
public interface GymDAO {

    @Query("SELECT * FROM gym")
    List<Gym> getAll();

    @Insert
    void insertAll(Gym... gyms);

    @Delete
    void delete(Gym gym);
}
