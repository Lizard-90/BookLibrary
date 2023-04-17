package com.example.data.DB.repasitories.books

import com.example.domain.entities.Volume
import com.example.domain.common.Result
import com.example.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImp(
    private val localDataSource: BooksLocalDataSource,
    private val remoteDataSource: BooksRemoteDataSource

) : BooksRepository {
     override suspend fun getRemoteBooks(author: String) : Result<List<Volume>>{
        return remoteDataSource.getBooks(author)
    }

    override suspend fun  getBookmarks(): Flow<List<Volume>> {
        return localDataSource.getBookmarks()
    }

    override suspend fun bookmark(book: Volume){
        localDataSource.bookmark(book)
    }

    override suspend fun unbookmark(book: Volume){
        localDataSource.unbookmark(book)
    }

}