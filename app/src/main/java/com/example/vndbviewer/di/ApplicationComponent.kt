package com.example.vndbviewer.di

import android.app.Application
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import com.example.vndbviewer.presentation.viewmodels.VnListViewModel
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun vnItemViewModel(): VnItemViewModel.Factory

    fun vnListViewModel(): VnListViewModel.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}