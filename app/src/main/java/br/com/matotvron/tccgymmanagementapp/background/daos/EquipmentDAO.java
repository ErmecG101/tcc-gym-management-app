package br.com.matotvron.tccgymmanagementapp.background.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.models.Equipments;

@Dao
public interface EquipmentDAO {
    @Insert
    void insertAll(Equipments... equipments);

    @Delete
    void delete(Equipments equipments);

    @Query("SELECT * FROM equipments")
    List<Equipments> getAll();
}
