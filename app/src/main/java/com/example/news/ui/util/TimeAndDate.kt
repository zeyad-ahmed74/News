package com.example.news.ui.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class TimeAndDate {

    companion object {

        fun dateFormat(oldDateFormat: String?): String? {
            val newDate: String?
            val dateFormat = SimpleDateFormat("h:mm a", Locale(getCountry()))

            newDate = try{
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldDateFormat)
                dateFormat.format(date)
            } catch (e: ParseException){
                 e.printStackTrace()
                oldDateFormat
            }
            return newDate
        }

        private fun getCountry():String{
            val locate = Locale.getDefault()
            val country = locate.country
            return country.lowercase(Locale.getDefault())
        }
    }
}