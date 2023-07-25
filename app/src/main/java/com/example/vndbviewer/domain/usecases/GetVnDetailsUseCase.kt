package com.example.vndbviewer.domain.usecases

import androidx.lifecycle.LiveData
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository

class GetVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    operator fun invoke(id: String): LiveData<Vn> {
        return vnListRepository.getVnDetails(id)
    }
}