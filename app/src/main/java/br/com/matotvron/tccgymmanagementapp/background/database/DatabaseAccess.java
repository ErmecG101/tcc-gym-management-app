package br.com.matotvron.tccgymmanagementapp.background.database;

public class DatabaseAccess {

    private static AppDatabase database;

    public static AppDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(AppDatabase database) {
        DatabaseAccess.database = database;
    }
}
