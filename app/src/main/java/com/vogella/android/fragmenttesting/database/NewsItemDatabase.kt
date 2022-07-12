package com.vogella.android.fragmenttesting.database

import androidx.room.*

// tableName is your table name
@Entity(tableName = "news_item")
class NewsItemDatabase constructor() {
    @field:PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @field:ColumnInfo(name = "image_url")
    var articleUrl: String? = null

    @field:ColumnInfo(name = "title")
    var articleTitle: String? = null

    @field:ColumnInfo(name = "description")
    var articleDescription: String? = null

    @field:ColumnInfo(name = "published_time")
    var articlePublishedTime: String? = null

    @field:ColumnInfo(name = "url")
    var url: String? = null

    @field:ColumnInfo(name = "author")
    var articleAuthor: String? = null

    @field:ColumnInfo(name = "content")
    var articleContent: String? = null

    @Ignore
    constructor(articleUrl: String?, articleTitle: String?, articleDescription: String?, articlePublishedTime: String?, url: String?, articleAuthor: String?, articleContent: String?) : this() {
        this.articleUrl = articleUrl
        this.articleTitle = articleTitle
        this.articleDescription = articleDescription
        this.articlePublishedTime = articlePublishedTime
        this.url = url
        this.articleAuthor = articleAuthor
        this.articleContent = articleContent
    }

}