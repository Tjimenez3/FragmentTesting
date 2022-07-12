package com.vogella.android.fragmenttesting.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondApi(private val baseUrl: String) {

    private fun provideFakeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun provideFakeApiCall(): APIRequest {
        return provideFakeRetrofit().create(APIRequest::class.java)
    }
}