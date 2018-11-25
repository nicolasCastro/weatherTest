package com.ncastro.weatherastrpay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ncastro.weatherastrpay.injection.DaggerViewModelInjector
import com.ncastro.weatherastrpay.injection.NetworkModule
import com.ncastro.weatherastrpay.injection.ViewModelInjector

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is ServiceViewModel -> injector.inject(this)
        }
    }
}