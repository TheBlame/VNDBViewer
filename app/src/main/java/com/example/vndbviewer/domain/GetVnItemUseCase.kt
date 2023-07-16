package com.example.vndbviewer.domain

class GetVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    suspend fun getVnDetails(id: String): Vn {
        return vnListRepository.getVnDetails(id)
    }
}