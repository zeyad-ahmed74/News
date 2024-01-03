package com.example.news.data.source.local

import androidx.room.TypeConverter
import com.example.news.data.model.Source
import com.google.gson.Gson

class Converts {
    @TypeConverter
    fun fromSource(source: Source):String?{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source {
        return Source(name,name)
    }

//    @TypeConverter
//    fun fromAnyToGson(any: Any):String{
//        val gson = Gson()
//        return gson.toJson(any)
//    }
//
//    @TypeConverter
//    fun fromGsonToAny(str:String):Any {
//        val gson = Gson()
//        return gson.fromJson(str,Any::class.java)
//    }

}