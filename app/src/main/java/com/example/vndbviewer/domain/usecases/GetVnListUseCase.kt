package com.example.vndbviewer.domain.usecases

import androidx.paging.PagingData
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVnListUseCase @Inject constructor(private val vnListRepository: VnListRepository) {

    operator fun invoke(): Flow<PagingData<Vn>> {
        return vnListRepository.getVnList()
    }
}