package com.ncastro.weatherastrpay.viewmodel

import com.ncastro.weatherastrpay.model.WeatherResult

interface ItemClickListener {
    fun onClick(item: WeatherResult)
}