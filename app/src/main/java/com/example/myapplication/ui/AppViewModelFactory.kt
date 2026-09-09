package com.example.myapplication.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.PostRepository
import com.example.myapplication.data.ThemeRepository

class AppViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.getDatabase(context)
        val postRepository = PostRepository(database.postDao())
        val themeRepository = ThemeRepository(context)

        return when {
            modelClass.isAssignableFrom(PostsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                PostsViewModel(postRepository) as T
            }
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ThemeViewModel(themeRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
