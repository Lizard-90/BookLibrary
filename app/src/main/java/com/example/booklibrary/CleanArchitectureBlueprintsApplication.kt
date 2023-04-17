package com.example.booklibrary

import android.app.Application
import android.os.Bundle
import com.example.DI.ServiceLocator
import com.example.booklibrary.mappers.BookWithStatusMapper
import com.example.data.DB.repasitories.books.BooksRepositoryImp
import com.example.domain.repositories.BooksRepository
import com.example.domain.usecases.BookmarkBookUseCase
import com.example.domain.usecases.GetBookmarkUseCase
import com.example.domain.usecases.GetBooksUseCase
import com.example.domain.usecases.UnbookmarkUseCase
import timber.log.Timber

class CleanArchitectureBlueprintsApplication : Application() {
    private val booksRepository: BooksRepositoryImp
        get() = ServiceLocator.provideBooksRepository(this)

    val getBooksUseCase: GetBooksUseCase
        get() = GetBooksUseCase(booksRepository)

    val getBookmarksUseCase: GetBookmarkUseCase
        get() = GetBookmarkUseCase(booksRepository)

    val bookmarkBooksUseCase: BookmarkBookUseCase
        get() = BookmarkBookUseCase(booksRepository)

    val unbookmarkBookUseCase: UnbookmarkUseCase
        get() = UnbookmarkUseCase(booksRepository)

    val bookWithStatusMapper = BookWithStatusMapper()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}