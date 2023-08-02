package com.example.vndbviewer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.vndbviewer.di.IdQualifier
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.GetVnDetailsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VnItemViewModel @Inject constructor(
    private val getVnDetailsUseCase: GetVnDetailsUseCase,
    @IdQualifier private val arg: String
) : ViewModel() {


    val vnDetails: Flow<Vn> = getVnDetailsUseCase.invoke(arg)
}