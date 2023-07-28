package com.example.vndbviewer.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface VnListRepository {

    fun getVnList(): Flow<PagingData<Vn>>

    fun getVnDetails(id: String): Flow<Vn>
}