package com.example.vndbviewer.domain

import androidx.lifecycle.LiveData

interface VnListRepository {

    fun getVnList(): LiveData<List<Vn>>

    fun getVnDetails(id: String): LiveData<Vn>
}