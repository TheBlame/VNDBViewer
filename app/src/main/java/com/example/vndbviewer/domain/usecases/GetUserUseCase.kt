package com.example.vndbviewer.domain.usecases

import com.example.vndbviewer.domain.User
import com.example.vndbviewer.domain.VnListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val vnListRepository: VnListRepository) {

    operator fun invoke(token: String): Flow<User?> {
        return vnListRepository.getUser(token)
    }
}