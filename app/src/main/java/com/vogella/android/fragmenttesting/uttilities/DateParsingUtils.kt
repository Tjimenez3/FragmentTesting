package com.vogella.android.fragmenttesting.uttilities

import com.vogella.android.fragmenttesting.constants.FormatConstant.INPUT_DATE_FORMAT
import com.vogella.android.fragmenttesting.constants.FormatConstant.OUTPUT_DATE_FORMAT
import timber.log.Timber
import java.text.SimpleDateFormat

class DateParsingUtils {

    fun parse(dateString: String, dateFormatInput: String, dateFormatOutput: String): String {
        try {
            val date = SimpleDateFormat(dateFormatInput).parse(dateString)
            return SimpleDateFormat(dateFormatOutput).format(date)
        }
        catch (e: Exception) {
            Timber.e(e)
            return ""
        }
    }
    fun getLongDate(dateString: String, format: String): Long {
        return SimpleDateFormat(format).parse(dateString).time
    }
    fun getinputFormat(): String {
        return INPUT_DATE_FORMAT
    }
    fun getOutputFormat(): String {
        return OUTPUT_DATE_FORMAT
    }
}