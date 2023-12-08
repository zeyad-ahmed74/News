package com.example.news.ui

sealed class PostIntent {
    data object GetArabicNews : PostIntent()
    data object GetEnglishNews : PostIntent()
}