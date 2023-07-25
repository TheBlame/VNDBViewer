package com.example.vndbviewer.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vndbviewer.data.VnListRepositoryImp
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.GetVnDetailsUseCase

class VnItemViewModel(application: Application, arg: String) : AndroidViewModel(application) {

    private val repository = VnListRepositoryImp(application)

    private val getVnDetailsUSeCase = GetVnDetailsUseCase(repository)

    val vnDetails: LiveData<Vn> = getVnDetailsUSeCase.invoke(arg)
}