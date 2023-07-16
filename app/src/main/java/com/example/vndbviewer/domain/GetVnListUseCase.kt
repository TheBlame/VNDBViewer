package com.example.vndbviewer.domain

class GetVnListUseCase(private val vnListRepository: VnListRepository) {

    suspend fun getVnList(): List<Vn> {
        return vnListRepository.getVnList()
    }
}