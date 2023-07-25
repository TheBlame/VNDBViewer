package com.example.vndbviewer.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.vndbviewer.data.network.pojo.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.network.pojo.VnBasicInfoDbModel
import com.example.vndbviewer.data.network.pojo.VnFullInfo
import com.example.vndbviewer.domain.Vn

@Dao
interface VnDao {

    @Query("SELECT * FROM vn_basic_info ORDER BY rating DESC, votecount DESC")
    fun getVnList(): LiveData<List<VnBasicInfoDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnList(vnList: List<VnBasicInfoDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnAdditionalInfo(vn: VnAdditionalInfoDbModel)

    @Transaction
    @Query("SELECT * FROM vn_basic_info WHERE id == :id LIMIT 1")
    suspend fun getVnFullInfo(id: String): VnFullInfo
}