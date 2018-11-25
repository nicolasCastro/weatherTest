package com.ncastro.weatherastrpay.adapter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ncastro.weatherastrpay.BuildConfig
import com.ncastro.weatherastrpay.R
import com.ncastro.weatherastrpay.model.WeatherResult

class WeatherViewModel : BaseViewModel() {

    companion object {
        const val CITY_FORMAT = "%s, %s"
    }

    val cityName = MutableLiveData<String>()
    val weather = MutableLiveData<String>()
    val icon = MutableLiveData<String>()
    val temp = MutableLiveData<String>()
    val tempRange = MutableLiveData<String>()
    val wind = MutableLiveData<String>()
    val clouds = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()

    fun bind(context: Context, weather: WeatherResult) {
        this.cityName.value = String.format(CITY_FORMAT, weather.name, weather.sys?.country ?: "")
        this.icon.value = String.format(BuildConfig.API_ICON, weather.weather?.get(0)?.icon)
        this.weather.value = weather.weather?.get(0)?.main ?: ""
        this.temp.value = context.getString(R.string.temperature_format, weather.main?.temp.toString())
        this.tempRange.value = context.getString(R.string.temperature_range,
                weather.main?.tempMin.toString(), weather.main?.tempMax.toString())
        this.wind.value = context.getString(R.string.wind_format, weather.wind?.speed.toString())
        this.clouds.value = context.getString(R.string.clouds_format, weather.clouds?.all.toString())
        this.pressure.value = context.getString(R.string.pressure_format, weather.main?.pressure.toString())
    }
    
    

}