package com.example.news.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.model.ArticlesItem

@Dao
interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookMark(articlesItem: ArticlesItem)

    @Query("select * from BookMarks")
    fun getBookMarks():LiveData<List<ArticlesItem>?>

    @Delete
    suspend fun deleteBookMark(articlesItem: ArticlesItem)


}