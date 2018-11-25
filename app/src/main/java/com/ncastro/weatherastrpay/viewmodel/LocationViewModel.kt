package com.ncastro.weatherastrpay.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices

class LocationViewModel(application: Application): AndroidViewModel(application) {

    private var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    fun getLastLocation(context: Context, block: (Location) -> Unit) {

        mFusedLocationClient!!.lastLocation.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful && task.result != null) {
                block.invoke(task.result!!)
            } else {
                throw Throwable(task.exception)
            }
        }
    }
}