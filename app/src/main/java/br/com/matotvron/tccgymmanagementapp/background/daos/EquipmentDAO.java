package br.com.matotvron.tccgymmanagementapp.background.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;

@Dao
public interface EquipmentDAO {
    @Insert
    void insertAll(Equipment... equipment);

    @Delete
    void delete(Equipment equipment);

    @Query("SELECT * FROM equipments")
    List<Equipment> getAll();
}
