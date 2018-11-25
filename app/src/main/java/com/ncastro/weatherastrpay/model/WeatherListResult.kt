package com.ncastro.weatherastrpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherListResult(@SerializedName("list") @Expose val weathers: List<WeatherResult>)