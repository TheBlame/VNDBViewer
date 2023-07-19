package com.example.vndbviewer.domain.usecases

import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository

class GetVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    suspend operator fun invoke(id: String): Vn {
        return vnListRepository.getVnDetails(id)
    }
}