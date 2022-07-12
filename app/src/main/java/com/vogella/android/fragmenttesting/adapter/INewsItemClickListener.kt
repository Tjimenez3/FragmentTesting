package com.vogella.android.fragmenttesting.adapter

import com.vogella.android.fragmenttesting.entity.NewsItem

interface INewsItemClickListener: IOnItemClickListener<NewsItem> {
    fun onTitleClick(title: String?)
    fun onImageClick(url: String?)
}