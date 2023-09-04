package com.example.vndbviewer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.vndbviewer.data.VnListRepositoryImpl
import com.example.vndbviewer.data.database.AppDatabase
import com.example.vndbviewer.data.network.api.ApiService
import com.example.vndbviewer.domain.VnListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindVnListRepository(impl: VnListRepositoryImpl): VnListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideDb(
            application: Application
        ): AppDatabase {
            return AppDatabase.getInstance(application)
        }

        @ApplicationScope
        @Provides
        fun provideService(): ApiService {
            return ApiService.create()
        }

        @ApplicationScope
        @Provides
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        }
    }
}