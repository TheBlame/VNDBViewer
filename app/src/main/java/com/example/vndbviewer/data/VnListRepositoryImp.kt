package com.example.vndbviewer.data

import android.app.Application
import com.example.vndbviewer.data.database.AppDatabase
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository

class VnListRepositoryImp(application: Application) : VnListRepository {

    private val db = AppDatabase.getInstance(application)

    override suspend fun getVnList(): List<Vn> {
        return db.vnDao().getVnList()
    }

    override suspend fun getVnDetails(id: String): Vn {
        return db.vnDao().getVnDetails(id)
    }

    override suspend fun addVnList(list: List<Vn>) {
        db.vnDao().insertVnList(list)
    }

    override suspend fun updateVnDetails(list: List<Vn>) {
        db.vnDao().updateVn(list)
    }
}