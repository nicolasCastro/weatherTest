package com.ncastro.weatherastrpay.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ncastro.weatherastrpay.BuildConfig
import com.ncastro.weatherastrpay.R
import com.ncastro.weatherastrpay.adapter.ItemDecoration
import com.ncastro.weatherastrpay.databinding.CardItemBinding
import com.ncastro.weatherastrpay.databinding.WeatherItemBinding
import com.ncastro.weatherastrpay.viewmodel.LocationViewModel
import com.ncastro.weatherastrpay.viewmodel.PermissionViewModel
import com.ncastro.weatherastrpay.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var permissionViewModel: PermissionViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var serviceViewModel: ServiceViewModel

    private var errorSnackBar: Snackbar? = null
    private lateinit var itemDecoration: ItemDecoration
    private var locationListener = { location: Location ->
        serviceViewModel.loadLocationWeather(location.latitude, location.longitude)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        loadItem()
    }

    private fun initViewModel() {
        permissionViewModel = ViewModelProviders.of(this).get(PermissionViewModel::class.java)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        serviceViewModel = ViewModelProviders.of(this).get(ServiceViewModel::class.java)

        initRecyclerView()

        serviceViewModel.loadingVisibility.observe(this, Observer {
            loader.visibility = it
        })
        serviceViewModel.errorMessage.observe(this, Observer {
            if (it != null) showError(it) else hideError()
        })
        serviceViewModel.loadCitiesWeather()

        itemButton.setOnClickListener { locationViewModel.getLastLocation(this, locationListener) }
    }

    private fun initRecyclerView() {
        var recyclerOrientation: Int = RecyclerView.HORIZONTAL
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerOrientation = RecyclerView.VERTICAL
        }
        itemDecoration = ItemDecoration(this)
        recyclerViewCities.addItemDecoration(itemDecoration)
        recyclerViewCities.layoutManager = LinearLayoutManager(this, recyclerOrientation, false)
        recyclerViewCities.adapter = serviceViewModel.adapter
    }

    public override fun onStart() {
        super.onStart()

        if (!permissionViewModel.checkPermissions()) {
            permissionViewModel.setupPermissions(this)
        } else {
            itemButton.visibility = View.VISIBLE
            locationViewModel.getLastLocation(this, locationListener)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionViewModel.LOCATION_PERMISSION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    permissionViewModel.makeDialog(
                            R.string.permission_request_dialog,
                            R.string.permission_request_title,
                            DialogInterface.OnClickListener { _, _ ->
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts(PermissionViewModel.PACKAGE, BuildConfig.APPLICATION_ID, null)
                                intent.data = uri
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            })
                } else {
                    itemButton.visibility = View.VISIBLE
                    locationViewModel.getLastLocation(this, locationListener)
                }
            }
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackBar = Snackbar.make(main_container, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.error_retry) { serviceViewModel.loadCitiesWeather() }
        errorSnackBar?.show()
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    private fun loadItem() {
        val binding: CardItemBinding = DataBindingUtil.bind(card_view)!!
        serviceViewModel.selectedItem.observe(this, Observer {
            binding.root.visibility = if (it == null) View.GONE else View.VISIBLE
            binding.viewModel = it
        })
    }
}