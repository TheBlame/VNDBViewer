package com.example.vndbviewer.di

import com.example.vndbviewer.presentation.fragments.TagsFragment
import com.example.vndbviewer.presentation.fragments.VnDetailsFragment
import com.example.vndbviewer.presentation.fragments.VnListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface FragmentComponent {

    fun inject(fragment: VnListFragment)

    fun inject(fragment: VnDetailsFragment)

    fun inject(fragment: TagsFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @IdQualifier id: String
        ): FragmentComponent
    }
}