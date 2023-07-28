package com.example.vndbviewer.domain.usecases

import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.flow.Flow

class GetVnDetailsUseCase(private val vnListRepository: VnListRepository) {

    operator fun invoke(id: String): Flow<Vn> {
        return vnListRepository.getVnDetails(id)
    }
}