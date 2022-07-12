package com.vogella.android.fragmenttesting.database


interface QueriesInterface {

    fun getAllNewsItems(): List<NewsItemDatabase>?

    fun getNewsItem(title: String): NewsItemDatabase?

    fun insertThreads(vararg threads: NewsItemDatabase): Int

}