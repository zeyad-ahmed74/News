package com.example.news.ui.homescreen

sealed class PostIntent {
    data object GetArabicNews : PostIntent()
    data object GetEnglishNews : PostIntent()
}