package com.ncastro.weatherastrpay.viewmodel

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.ncastro.weatherastrpay.R

class PermissionViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val LOCATION_PERMISSION = 34
        const val PACKAGE = "package"
    }

    fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(context(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun setupPermissions(context: Context) {
        val permission = ContextCompat.checkSelfPermission(context(), Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
                            Manifest.permission.RECORD_AUDIO)) {
                makeDialog(R.string.permission_request_dialog,
                        R.string.permission_request_title,
                        DialogInterface.OnClickListener { _, _ -> makeRequest(context) })
            } else {
                makeRequest(context)
            }
        }
    }

    private fun makeRequest(context: Context) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION)
    }

    fun makeDialog(@StringRes message: Int, @StringRes title: Int,
                   listener: DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(context())
        builder.setMessage(context().getString(message))
                .setTitle(context().getString(title))
        builder.setPositiveButton(context().getString(R.string.ok), listener)
        val dialog = builder.create()
        dialog.show()
    }

    private fun context() = getApplication() as Context
}