package com.example.booklibrary.presentation

import com.example.booklibrary.entities.BookWithStatus
import com.example.booklibrary.mappers.BookWithStatusMapper
import com.example.domain.entities.Volume
import com.example.domain.usecases.BookmarkBookUseCase
import com.example.domain.usecases.GetBooksUseCase
import com.example.domain.usecases.UnbookmarkUseCase
import com.example.domain.common.Result
import com.example.domain.usecases.GetBookmarkUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.*

class BooksViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val getBookmarksUseCase: GetBookmarkUseCase,
    private val bookmarkBookUseCase: BookmarkBookUseCase,
    private val unbookmarkBookUseCase: UnbookmarkUseCase,
    private val mapper: BookWithStatusMapper
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _books = MutableLiveData<List<BookWithStatus>>()
    val books = _books

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _remoteBooks = arrayListOf<Volume>()

    // Getting books with uncle bob as default author :)
    fun getBooks(author: String) {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val booksResult = getBooksUseCase.invoke(author)) {
                is Result.Success -> {
                    _remoteBooks.clear()
                    _remoteBooks.addAll(booksResult.data)

                    val bookmarksFlow = getBookmarksUseCase.invoke()
                    bookmarksFlow.collect { bookmarks ->
                        books.value = mapper.fromVolumeToBookWithStatus(_remoteBooks, bookmarks)
                        _dataLoading.postValue(false)
                    }
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    books.value = emptyList()
                    _error.postValue(booksResult.exception.message)
                }
            }
        }
    }

    fun bookmark(book: BookWithStatus) {
        viewModelScope.launch {
            bookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
        }
    }

    fun unbookmark(book: BookWithStatus) {
        viewModelScope.launch {
            unbookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
        }
    }

    class BooksViewModelFactory(
        private val getBooksUseCase: GetBooksUseCase,
        private val getBookmarksUseCase: GetBookmarkUseCase,
        private val bookmarkBookUseCase: BookmarkBookUseCase,
        private val unbookmarkBookUseCase: UnbookmarkUseCase,
        private val mapper: BookWithStatusMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BooksViewModel(
                getBooksUseCase,
                getBookmarksUseCase,
                bookmarkBookUseCase,
                unbookmarkBookUseCase,
                mapper
            ) as T
        }
    }
}