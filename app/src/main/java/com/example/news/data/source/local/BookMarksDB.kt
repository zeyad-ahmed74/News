package com.example.news.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.news.data.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1 , exportSchema = false)
@TypeConverters(Converts::class)
abstract class BookMarksDB : RoomDatabase(){

    abstract fun getBookMarkDao():BookMarkDao

    companion object{
        @Volatile
        private var Instance:BookMarksDB?=null

        fun getBookMarkDataBase(context: Context):BookMarksDB{

           return Instance ?: synchronized(this){
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   BookMarksDB::class.java,
                   "bookmark_database",
               ).build()
               Instance = instance
               // return instance
               instance
           }
        }
    }

}