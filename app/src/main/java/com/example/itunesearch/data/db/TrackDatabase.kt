package com.example.itunesearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.itunesearch.data.model.Track

/**
 * Created by Rajat Sangrame on 16/6/20.
 * http://github.com/rajatsangrame
 */
@Database(entities = [Track::class], version = 1)
abstract class TrackDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    companion object {
        @Volatile
        private var INSTANCE: TrackDatabase? = null

        fun getDataBase(context: Context): TrackDatabase? {
            if (INSTANCE == null) {
                synchronized(TrackDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TrackDatabase::class.java, "track_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}