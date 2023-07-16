package com.example.vndbviewer.domain

class UpdateVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    suspend fun updateVnDetails(list: List<Vn>) {
        return vnListRepository.updateVnDetails(list)
    }
}