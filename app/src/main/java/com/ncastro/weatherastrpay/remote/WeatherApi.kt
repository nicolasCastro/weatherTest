package com.ncastro.weatherastrpay.remote

import com.ncastro.weatherastrpay.BuildConfig
import com.ncastro.weatherastrpay.model.WeatherListResult
import com.ncastro.weatherastrpay.model.WeatherResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

const val APP_ID_PARAM = "APPID"
interface WeatherApi {

    @GET("weather")
    fun getCurrentWeather(@QueryMap map: Map<String, String>, @Query("units") units: String = "metric",
                          @Query(APP_ID_PARAM) app_key: String = BuildConfig.API_KEY): Observable<WeatherResult>

    @GET("group")
    fun getCitiesWeather(@Query("id") cities: String, @Query("units") units: String = "metric",
                         @Query(APP_ID_PARAM) app_key: String = BuildConfig.API_KEY): Observable<WeatherListResult>
}