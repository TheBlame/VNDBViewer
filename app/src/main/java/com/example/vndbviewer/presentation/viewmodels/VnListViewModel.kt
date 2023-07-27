package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.domain.usecases.GetVnListUseCase
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class VnListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnListUseCase = GetVnListUseCase(repository)

    val vnList = getVnListUseCase.invoke()
        .cachedIn(viewModelScope)
        .onStart { Log.d("flow", "start") }
        .onCompletion { Log.d("flow", "complete") }

}