package com.example.vndbviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vndbviewer.network.pojo.VnList

@Dao
interface VnDao {
    @Query("SELECT * FROM vn ORDER BY rating DESC")
    fun getVnList(): LiveData<List<com.example.vndbviewer.network.pojo.VnList>>

    @Query("SELECT * FROM vn WHERE id == :id LIMIT 1")
    fun getVnDetails(id: String): LiveData<VnList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnList(vnList: List<VnList>)
}