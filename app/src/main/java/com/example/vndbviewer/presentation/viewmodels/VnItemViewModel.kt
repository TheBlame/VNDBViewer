package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.GetVnDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class VnItemViewModel(application: Application, arg: String) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnDetailsUSeCase = GetVnDetailsUseCase(repository)

    val vnDetails: Flow<Vn> = getVnDetailsUSeCase.invoke(arg)
        .shareIn(viewModelScope,
        started = SharingStarted.Lazily,
        replay = 1)
}