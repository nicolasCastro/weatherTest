package com.ncastro.weatherastrpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds(@SerializedName("all") @Expose val all: Int = 0)