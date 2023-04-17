package com.example.domain.usecases

import com.example.domain.entities.Volume
import com.example.domain.repositories.BooksRepository

class UnbookmarkUseCase (private val bookRepository: BooksRepository){
    suspend operator fun invoke(book: Volume) = bookRepository.unbookmark(book)
}