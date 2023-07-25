package com.example.vndbviewer.domain

import androidx.lifecycle.LiveData

interface VnListRepository {

    fun getVnList(): LiveData<List<Vn>>

    suspend fun getVnDetails(id: String): Vn

    suspend fun addVnList(list: List<Vn>)

    suspend fun updateVnDetails(vn: Vn)
}