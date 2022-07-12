package com.vogella.android.fragmenttesting.extension

import android.content.Context
import java.util.*

fun Context?.changeLocalization(locale: Locale) {

    this?.resources?.configuration?.let { config ->
        config.setLocale(locale)

        this.createConfigurationContext(config)

        this.resources?.updateConfiguration(config, this.resources?.displayMetrics)
    }
}