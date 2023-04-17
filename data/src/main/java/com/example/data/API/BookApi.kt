package com.example.data.API


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("books/v1/volumes")
    suspend fun getBook(@Query(value = "q") author: String) : Response<BooksApiResponse>
}