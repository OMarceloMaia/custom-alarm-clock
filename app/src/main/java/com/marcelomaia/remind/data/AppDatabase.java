package com.marcelomaia.remind.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase INSTANCE;

    public abstract AlarmDao alarmDao();

    public static  AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            String packageName = context.getPackageName();
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, packageName + ".db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

//    public static AppDatabase getInMemoryDatabase(Context context) {
//        if (INSTANCE == null) {
////            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-alarms").build();
//            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).allowMainThreadQueries().build();
//        }
//        return INSTANCE;
//    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
