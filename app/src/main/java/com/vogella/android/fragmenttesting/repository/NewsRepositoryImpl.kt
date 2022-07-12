package com.vogella.android.fragmenttesting.repository

import com.vogella.android.fragmenttesting.api.APIRequest
import com.vogella.android.fragmenttesting.entity.LoginResponse
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.database.DatabaseService
import com.vogella.android.fragmenttesting.database.NewsItemDatabase
import timber.log.Timber

class NewsRepositoryImpl(private val api: APIRequest, private val database: DatabaseService): INewsRepository {
    override suspend fun fetchNews(): List<NewsItem>? {
        val newsItems = database.getAllNewsItems()
        if (newsItems?.isEmpty() == false) {
            Timber.e("Already something in database")

            val list1 = newsItems.map { item ->
               NewsItem(
                   item.articleUrl,
                   item.articleTitle,
                   item.articleDescription,
                   item.articlePublishedTime,
                   item.url,
                   item.articleAuthor,
                   item.articleContent)
            }
            return list1.toList()

        }
        else {
            Timber.e("Calling Api again")
            val apiNewsItems = api.getNews()

            apiNewsItems.articleList?.forEach {
                database.insertThreads(NewsItemDatabase(it.urlToImage,it.title,it.description,it.publishedAt,it.url,it.author,it.content))
            }
            return apiNewsItems.articleList
        }
    }

    override suspend fun fetchFakeCall(): LoginResponse {
        return api.getFakeCall()
    }


}