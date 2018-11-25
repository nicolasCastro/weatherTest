package com.ncastro.weatherastrpay.injection

import com.ncastro.weatherastrpay.adapter.WeatherViewModel
import com.ncastro.weatherastrpay.viewmodel.ServiceViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(postListViewModel: ServiceViewModel)
    fun inject(weatherViewModel: WeatherViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}