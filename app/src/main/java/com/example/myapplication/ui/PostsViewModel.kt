package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Post
import com.example.myapplication.data.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: PostRepository) : ViewModel() {
    val posts: StateFlow<List<Post>> = repository.observePosts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addPost(content: String) {
        viewModelScope.launch {
            repository.addPost(content)
        }
    }

    fun editPost(post: Post, newContent: String) {
        viewModelScope.launch {
            repository.editPost(post, newContent)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.removePost(post)
        }
    }
}
