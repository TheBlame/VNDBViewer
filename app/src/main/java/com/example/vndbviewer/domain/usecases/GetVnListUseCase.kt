package com.example.vndbviewer.domain.usecases

import androidx.lifecycle.LiveData
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository

class GetVnListUseCase(private val vnListRepository: VnListRepository) {

    operator fun invoke(): LiveData<List<Vn>> {
        return vnListRepository.getVnList()
    }
}