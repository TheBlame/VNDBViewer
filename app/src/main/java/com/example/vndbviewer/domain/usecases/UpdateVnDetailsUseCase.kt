package com.example.vndbviewer.domain.usecases

import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository

class UpdateVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    suspend operator fun invoke(vn: Vn) {
        vnListRepository.updateVnDetails(vn)
    }
}