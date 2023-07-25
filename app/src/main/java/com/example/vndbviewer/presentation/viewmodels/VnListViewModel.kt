package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.domain.usecases.GetVnListUseCase

class VnListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnListUseCase = GetVnListUseCase(repository)

    val vnList = getVnListUseCase()
}