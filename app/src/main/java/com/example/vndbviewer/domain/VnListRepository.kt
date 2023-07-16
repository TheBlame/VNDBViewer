package com.example.vndbviewer.domain

interface VnListRepository {

    suspend fun getVnList(): List<Vn>

    suspend fun getVnDetails(id: String): Vn

    suspend fun addVnList(list: List<Vn>)

    suspend fun updateVnDetails(list: List<Vn>)
}