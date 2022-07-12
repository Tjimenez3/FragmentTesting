package com.vogella.android.fragmenttesting.api

import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.entity.NewsModel
import retrofit2.http.GET

interface APIRequest {

    @GET("/v2/top-headlines?country=us&apiKey=efb395f5e5f942509e7408c327cb6e63")
    suspend fun getNews() : NewsModel

    @GET("/v1/7ad85c10-d745-480c-9e03-de7934c98a3a")
    suspend fun getFakeCall() : LoginResponse

}
