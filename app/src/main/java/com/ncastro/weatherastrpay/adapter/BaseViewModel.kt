package com.ncastro.weatherastrpay.adapter

import com.ncastro.weatherastrpay.injection.DaggerViewModelInjector
import com.ncastro.weatherastrpay.injection.NetworkModule
import com.ncastro.weatherastrpay.injection.ViewModelInjector

abstract class BaseViewModel {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is WeatherViewModel -> injector.inject(this)
        }
    }
}