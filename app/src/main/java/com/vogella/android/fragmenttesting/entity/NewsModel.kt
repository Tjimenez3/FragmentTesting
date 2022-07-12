package com.vogella.android.fragmenttesting.entity

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("articles")
    val articleList: List<NewsItem>?,
    val status: String?,
    val totalResults: Int
)