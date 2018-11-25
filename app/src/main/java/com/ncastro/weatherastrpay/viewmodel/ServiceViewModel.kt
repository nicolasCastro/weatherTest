package com.ncastro.weatherastrpay.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ncastro.weatherastrpay.R
import com.ncastro.weatherastrpay.adapter.WeatherAdapter
import com.ncastro.weatherastrpay.adapter.WeatherViewModel
import com.ncastro.weatherastrpay.model.WeatherListResult
import com.ncastro.weatherastrpay.model.WeatherResult
import com.ncastro.weatherastrpay.remote.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ServiceViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val LAT_PARAM = "lat"
        const val LON_PARAM = "lon"
        const val LONDON = 2643743
        const val SAO_PAULO = 3448439
        const val BUENOS_AIRES = 3435910
        const val MONTEVIDEO = 3441575
        const val MUNICH = 3220838
    }

    @Inject
    lateinit var apiClient: WeatherApi
    private var subscription: CompositeDisposable = CompositeDisposable()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val adapter: WeatherAdapter = WeatherAdapter()
    var selectedItem: MutableLiveData<WeatherViewModel> = MutableLiveData()

    fun loadLocationWeather(latitude: Double, longitude: Double) {
        subscription.add(apiClient.getCurrentWeather(getLocationMap(latitude, longitude))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> retrieveDataSuccess(result) },
                        { ex -> retrieveDataError(ex) }))
    }

    fun loadCitiesWeather() {
        subscription.add(apiClient.getCitiesWeather(getCitiesParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> retrieveDataSuccess(result) },
                        { ex -> retrieveDataError(ex) }))
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun retrieveDataSuccess(result: WeatherResult) {
        val viewModel = WeatherViewModel()
        viewModel.bind(getApplication(), result)
        selectedItem.value = viewModel
    }

    private fun retrieveDataSuccess(result: WeatherListResult) {
        adapter.update(result.weathers, object : ItemClickListener {
            override fun onClick(item: WeatherResult) {
                retrieveDataSuccess(item)
            }

        })
    }

    private fun retrieveDataError(exception: Throwable) {
        exception.printStackTrace()
        errorMessage.value = R.string.error
        selectedItem.value = null
    }

    private fun getLocationMap(latitude: Double, longitude: Double): Map<String, String> {
        val map: MutableMap<String, String> = hashMapOf()
        map[LAT_PARAM] = latitude.toString()
        map[LON_PARAM] = longitude.toString()

        return map
    }

    private fun getCitiesParam(): String {
        return TextUtils.join(",", listOf(MONTEVIDEO, LONDON, SAO_PAULO, BUENOS_AIRES, MUNICH))
    }
}