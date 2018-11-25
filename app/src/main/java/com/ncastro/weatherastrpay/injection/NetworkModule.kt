package com.ncastro.weatherastrpay.injection

import com.ncastro.weatherastrpay.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.ncastro.weatherastrpay.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object NetworkModule {

    private val TIMEOUT = 30L
    private val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Reusable
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
                .setVersion(1.0)
                .setDateFormat(SERVER_DATE_FORMAT)
        return gsonBuilder.create()
    }

    @Provides
    @Reusable
    internal fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}