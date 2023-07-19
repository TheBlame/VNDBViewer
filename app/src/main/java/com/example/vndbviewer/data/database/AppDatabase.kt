package com.example.vndbviewer.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vndbviewer.domain.Vn

@Database(entities = [Vn::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val instance =
                    Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun vnDao(): VnDao
}