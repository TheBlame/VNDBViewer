package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.data.network.api.ApiFactory
import com.example.vndbviewer.data.network.pojo.VnRequest
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.GetVnDetailsUseCase
import com.example.vndbviewer.domain.usecases.UpdateVnDetailsUseCase
import kotlinx.coroutines.launch

class VnItemViewModel(application: Application, arg: String) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnDetailsUSeCase = GetVnDetailsUseCase(repository)
    private val updateVnDetailsUseCase = UpdateVnDetailsUseCase(repository)

    private val _vnDetails = MutableLiveData<Vn>()
    val vnDetails: LiveData<Vn>
        get() = _vnDetails

    init {
        viewModelScope.launch { _vnDetails.value = getVnDetailsUSeCase.invoke(arg) }
    }
}