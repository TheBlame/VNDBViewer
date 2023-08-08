package com.example.vndbviewer.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vndbviewer.data.network.pojo.Tags
import com.example.vndbviewer.di.IdQualifier
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.usecases.GetVnDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class VnItemViewModel @Inject constructor(
    private val getVnDetailsUseCase: GetVnDetailsUseCase,
    @IdQualifier private val arg: String
) : ViewModel() {


    private val _vnDetails = MutableStateFlow<State?>(null)
    val vnDetails = _vnDetails.asStateFlow()

    init {
        viewModelScope.launch {
            getVnDetailsUseCase.invoke(arg).collectLatest {
                _vnDetails.value = State(vn = it)
                Log.d("vmcollect state", _vnDetails.value.toString())

            }
        }
    }

    fun changeSexualContent() {
        _vnDetails.value?.sexual = _vnDetails.value?.sexual != true
        Log.d("button", vnDetails.value.toString())
    }

    fun changeContent() {
        _vnDetails.value?.content = _vnDetails.value?.content != true
    }

    fun changeTechnical() {
        _vnDetails.value?.technical = _vnDetails.value?.technical != true
    }

    data class State(
        var content: Boolean = true,
        var sexual: Boolean = false,
        var technical: Boolean = true,
        var vn: Vn
    )
}