package com.example.vndbviewer.domain

class AddVnListUseCase(private val vnListRepository: VnListRepository) {

    suspend fun addVnList(list: List<Vn>) {
        vnListRepository.addVnList(list)
    }
}