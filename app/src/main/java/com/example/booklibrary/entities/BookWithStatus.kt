package com.example.booklibrary.entities

data class BookWithStatus (
    val id: String,
    val title: String,
    val author: List<String>,
    val imageUrl: String?,
    val status: BookMarkStatus
        )


