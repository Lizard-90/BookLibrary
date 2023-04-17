package com.example.domain.usecases

import com.example.domain.repositories.BooksRepository

class GetBooksUseCase(private val bookRepository: BooksRepository) {

    suspend operator fun invoke(author: String) = bookRepository.getRemoteBooks(author)
}