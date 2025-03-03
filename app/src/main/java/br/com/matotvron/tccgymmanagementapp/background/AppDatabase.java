package br.com.matotvron.tccgymmanagementapp.background;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.matotvron.tccgymmanagementapp.background.dao.GymDAO;
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;

@Database(entities = {Gym.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract GymDAO gymDAO();
}
