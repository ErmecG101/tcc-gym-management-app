package br.com.matotvron.tccgymmanagementapp.background;

import androidx.room.Room;

public class DatabaseAccess {
    private static AppDatabase database;



    public static AppDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(AppDatabase database){
        DatabaseAccess.database = database;
    }
}
