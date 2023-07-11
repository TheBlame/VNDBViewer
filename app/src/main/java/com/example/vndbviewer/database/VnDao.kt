package com.example.vndbviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VnDao {
    @Query("SELECT * FROM vn ORDER BY rating DESC")
    fun getVnList(): LiveData<List<com.example.vndbviewer.network.pojo.VnList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVnList(vnList: List<com.example.vndbviewer.network.pojo.VnList>)
}