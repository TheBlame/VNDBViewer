package com.example.vndbviewer.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.vndbviewer.domain.usecases.GetVnListUseCase
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class VnListViewModel @Inject constructor(private val getVnListUseCase: GetVnListUseCase) : ViewModel() {

    val vnList = getVnListUseCase.invoke()
        .cachedIn(viewModelScope)
        .onStart { Log.d("flow", "start") }
        .onCompletion { Log.d("flow", "complete") }
}