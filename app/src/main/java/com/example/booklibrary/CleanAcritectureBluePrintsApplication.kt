package com.example.booklibrary

import android.app.Application
import android.os.Bundle
import com.example.DI.ServiceLocator
import com.example.booklibrary.mappers.BookWithStatusMapper
import com.example.data.DB.repasitories.books.BooksRepositoryImp
import com.example.domain.repositories.BooksRepository
import com.example.domain.usecases.BookmarkBookUseCase
import com.example.domain.usecases.GetBooksUseCase
import com.example.domain.usecases.UnbookmarkUseCase
import timber.log.Timber

class CleanAcritectureBluePrintsApplication : Application() {
    private val booksRepository: BooksRepository
        get() = ServiceLocator.provideBooksRepository(this)

    val getBookUseCase: GetBooksUseCase
        get() = GetBooksUseCase(booksRepository)

    val getBoolmarksUseCase: GetBooksUseCase
        get() = GetBooksUseCase(booksRepository)

    val bookmarkBooksUseCase: BookmarkBookUseCase
        get() = BookmarkBookUseCase(booksRepository)

    val unbookmarkBooksUseCase: UnbookmarkUseCase
        get() = UnbookmarkUseCase(booksRepository)

    val bookWithStatusMapper = BookWithStatusMapper()

    fun onCreate(saveInstanceState: Bundle) {
        super.onCreate()
        Timber.plant(Timber.DebugTree());
    }
}