package com.shibainu.li.httplib.base

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {
    companion object{
        const val TIME_OUT = 5
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
//            addInterceptor()
            connectTimeout(TIME_OUT.toLong(),TimeUnit.SECONDS)
            handleBuilder(this)
        }.build()
    }


    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service{
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }

}