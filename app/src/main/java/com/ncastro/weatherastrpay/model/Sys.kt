package com.ncastro.weatherastrpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys(@SerializedName("message") @Expose val message: Double,
          @SerializedName("country") @Expose val country: String,
          @SerializedName("sunrise") @Expose val sunrise: Int,
          @SerializedName("sunset") @Expose val sunset: Int)