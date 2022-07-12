package com.vogella.android.fragmenttesting.entity

import android.os.Parcel
import android.os.Parcelable

data class NewsItem(val urlToImage: String?, val title: String?, val description: String?, val publishedAt: String?,val url: String?, val author: String?, val content: String?):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(urlToImage)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(publishedAt)
        parcel.writeString(url)
        parcel.writeString(author)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }
    }
}