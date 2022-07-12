package com.vogella.android.fragmenttesting.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Api(private val baseUrl: String) {
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun provideApiCall(): APIRequest {
        return provideRetrofit().create(APIRequest::class.java)
    }
}