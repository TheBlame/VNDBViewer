package com.example.vndbviewer.domain.usecases

import androidx.paging.PagingData
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.flow.Flow

class GetVnListUseCase(private val vnListRepository: VnListRepository) {

    operator fun invoke(): Flow<PagingData<Vn>> {
        return vnListRepository.getVnList()
    }
}