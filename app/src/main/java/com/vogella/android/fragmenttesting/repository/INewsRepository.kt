package com.vogella.android.fragmenttesting.repository

import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.database.DatabaseService

interface INewsRepository {
    suspend fun fetchNews(): List<NewsItem>?
    suspend fun fetchFakeCall(): LoginResponse
}