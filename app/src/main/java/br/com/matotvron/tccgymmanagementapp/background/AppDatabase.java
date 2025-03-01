package br.com.matotvron.tccgymmanagementapp.background;

import androidx.room.RoomDatabase;

import br.com.matotvron.tccgymmanagementapp.background.dao.GymDAO;

public abstract class AppDatabase  extends RoomDatabase {
    public abstract GymDAO gymDAO();
}
