package com.example.domain.usecases

import com.example.domain.repositories.BooksRepository

class GetBookmarkUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke() = booksRepository.getBookmarks()
}