package com.hashmac.islam360.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hashmac.islam360.interfaces.Dao;
import com.hashmac.islam360.model.TasbeehModel;

@Database(entities = {TasbeehModel.class}, version = 1)
public abstract class Islam360Database extends RoomDatabase {

    private static Islam360Database instance;
    public abstract Dao dao();

    public static synchronized Islam360Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Islam360Database.class, "islam360_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(Islam360Database instance) {
            Dao dao = instance.dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}