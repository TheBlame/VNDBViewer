package com.example.vndbviewer.presentation

import android.app.Application
import com.example.vndbviewer.di.DaggerApplicationComponent

class VndbApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}