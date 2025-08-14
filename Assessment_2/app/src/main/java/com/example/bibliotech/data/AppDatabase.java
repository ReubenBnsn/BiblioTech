package com.example.bibliotech.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Book.class, Member.class} , version = 2 , exportSchema = false) // ## exportSchema = false FOR NOW ONLY - to stop an error before JSON is configured
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    public abstract MemberDao memberDao();


    private static volatile AppDatabase INSTANCE;


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "biblio_db"
                    )
                    .fallbackToDestructiveMigration() // should stop the errors i hope :(
                    .build();
                }
            }
        }
        return INSTANCE;

    }
}
