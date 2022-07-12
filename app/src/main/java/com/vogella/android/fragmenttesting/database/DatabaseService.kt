package com.vogella.android.fragmenttesting.database

import android.content.Context

class DatabaseService(context: Context) : QueriesInterface {

    private val dao: RepoDao = AppDatabase.getInstance(context).repoDao()

    override fun getAllNewsItems(): List<NewsItemDatabase>? {
        return dao.allRepos as List<NewsItemDatabase>?
    }

    override fun getNewsItem(title: String): NewsItemDatabase? {
        return dao.getNewsItem(title)
    }

    override fun insertThreads(vararg threads: NewsItemDatabase): Int {
        return try {
            dao.insert(*threads)
            1

        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}