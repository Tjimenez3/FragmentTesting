package com.vogella.android.fragmenttesting.database

import androidx.room.*


@Dao
interface RepoDao {
    @get:Query("SELECT * FROM news_item")
    val allRepos: List<NewsItemDatabase?>?

    @Query("SELECT * FROM news_item WHERE title == :newsItemTitle")
    fun getNewsItem(vararg newsItemTitle: String): NewsItemDatabase?

    @Insert
    fun insert(vararg newsItemDatabases: NewsItemDatabase?)

    @Update
    fun update(vararg newsItemDatabases: NewsItemDatabase?)

    @Delete
    fun delete(vararg newsItemDatabases: NewsItemDatabase?)

}