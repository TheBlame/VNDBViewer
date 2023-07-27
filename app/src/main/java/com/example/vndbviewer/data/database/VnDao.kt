package com.example.vndbviewer.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.vndbviewer.data.database.dbmodels.VnAdditionalInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnBasicInfoDbModel
import com.example.vndbviewer.data.database.dbmodels.VnFullInfo

@Dao
interface VnDao {

    @Query("SELECT * FROM vn_basic_info ORDER BY rating DESC, votecount DESC")
    fun getVnList(): PagingSource<Int, VnBasicInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnList(vnList: List<VnBasicInfoDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVnAdditionalInfo(vn: VnAdditionalInfoDbModel)

    @Transaction
    @Query("SELECT * FROM vn_basic_info WHERE id == :id LIMIT 1")
    fun getVnFullInfo(id: String): LiveData<VnFullInfo>

    @Query("DELETE FROM vn_basic_info")
    suspend fun clearVnList()
}