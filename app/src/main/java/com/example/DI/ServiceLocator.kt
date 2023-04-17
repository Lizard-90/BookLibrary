package com.example.DI

import android.content.Context
import com.example.data.API.NetworkModel
import com.example.data.BuildConfig

import com.example.data.DB.BooksDatabase
import com.example.data.DB.repasitories.books.BooksLocalDataSource
import com.example.data.DB.repasitories.books.BooksLocalDataSourceImpl
import com.example.data.DB.repasitories.books.BooksRemoteDataSourceImpl
import com.example.data.DB.repasitories.books.BooksRepositoryImp
import com.example.data.mappers.BookApiResponseMapper
import com.example.data.mappers.BookEntityMapper
import kotlinx.coroutines.Dispatchers

object ServiceLocator {
    private var database: BooksDatabase? = null
    private val networkModule by lazy {
        NetworkModel()
    }
    private val bookEntityMapper by lazy {
        BookEntityMapper()
    }

    @Volatile
    var booksRepository: BooksRepositoryImp? = null

    fun provideBooksRepository(context: Context): BooksRepositoryImp {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return booksRepository ?: createBooksRepository(context)
        }
    }

    private fun createBooksRepository(context: Context): BooksRepositoryImp {
        val newRepo =
            BooksRepositoryImp(
                createBooksLocalDataSource(context),
                BooksRemoteDataSourceImpl(
                    networkModule.createBooksApi(BuildConfig.GOOGLE_APIS_ENDPOINT),
                    BookApiResponseMapper()
                )
            )
        booksRepository = newRepo
        return newRepo
    }

    private fun createBooksLocalDataSource(context: Context): BooksLocalDataSource {
        val database = database ?: createDataBase(context)
        return BooksLocalDataSourceImpl(
            database.bookDao(),
            Dispatchers.IO,
            bookEntityMapper
        )
    }

    private fun createDataBase(context: Context): BooksDatabase {
        val result = BooksDatabase.getDatabase(context)
        database = result
        return result

    }
}
