package com.example.vndbviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vndbviewer.network.pojo.Vn

@Dao
interface VnDao {
    @Query("SELECT * FROM vn ORDER BY rating DESC, votecount DESC")
    fun getVnList(): LiveData<List<Vn>>

    @Query("SELECT * FROM vn WHERE id == :id LIMIT 1")
    fun getVnDetails(id: String): LiveData<Vn>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnList(vnList: List<Vn>)

    @Update
    suspend fun updateVn(vnList: List<Vn>)
}