package com.example.vndbviewer.di

import androidx.lifecycle.ViewModel
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import com.example.vndbviewer.presentation.viewmodels.VnListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//@Module
//interface ViewModelModule {
//
//    @IntoMap
//    @ViewModelKey(VnListViewModel::class)
//    @Binds
//    fun bindVnListViewModel(viewModel: VnListViewModel): ViewModel
//
//    @IntoMap
//    @ViewModelKey(VnItemViewModel::class)
//    @Binds
//    fun bindVlItemViewModel(viewModel: VnItemViewModel): ViewModel
//}