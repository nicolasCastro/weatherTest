package com.ncastro.weatherastrpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Coord(@SerializedName("lon") @Expose val lon: Double? = null,
            @SerializedName("lat") @Expose val lat: Double? = null)