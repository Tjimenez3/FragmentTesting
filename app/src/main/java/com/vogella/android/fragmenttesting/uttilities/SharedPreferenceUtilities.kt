package com.vogella.android.fragmenttesting.uttilities

import android.content.SharedPreferences
import com.google.gson.Gson
import com.vogella.android.fragmenttesting.constants.PreferenceConstant.PREFERENCE_DEFAULT_STRING
import com.vogella.android.fragmenttesting.entity.NewsItem

class SharedPreferenceUtilities(private val preferences: SharedPreferences) {

    val editor = preferences.edit()

    private fun getPreferences(): SharedPreferences.Editor {
        return editor
    }

    fun setPreferencesString(itemName: String, item: String) {
        getPreferences().putString(itemName,item)
    }

    fun setPreferencesInt(itemName: String, item: Int) {
        getPreferences().putInt(itemName,item)
    }
    fun setPreferenceNewsItem(itemName: String, item: NewsItem) {
        val gson= Gson()
        val itemString = gson.toJson(item)
        getPreferences().putString(itemName,itemString)
    }

    fun setPreferencesFinished() {
        getPreferences().commit()
    }

    fun getPreferencesString(itemName: String): String? {
        return preferences.getString(itemName, null)
    }

    fun getPreferencesInt(itemName: String): Int?{
        return preferences.getInt(itemName,0)
    }

    fun getPreferenceNewsItem(itemName: String): NewsItem {
        val gson = Gson()
        val itemString = preferences.getString(itemName, null)
        return gson.fromJson(itemString,NewsItem::class.java)
    }

}