package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class PostRepository(private val postDao: PostDao) {
    fun observePosts(): Flow<List<Post>> = postDao.observeAll()

    suspend fun addPost(content: String) {
        postDao.insert(Post(content = content))
    }

    suspend fun editPost(post: Post, newContent: String) {
        postDao.update(post.copy(content = newContent))
    }

    suspend fun removePost(post: Post) {
        postDao.delete(post)
    }
}
