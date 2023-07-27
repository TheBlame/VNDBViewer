package com.example.vndbviewer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vndbviewer.data.database.dbmodels.RemoteKeys
import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel

@Database(
    entities = [VnBasicInfoDbModel::class, VnAdditionalInfoDbModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "main.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DB_NAME
            )
                .build()
    }

    abstract fun vnDao(): VnDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}