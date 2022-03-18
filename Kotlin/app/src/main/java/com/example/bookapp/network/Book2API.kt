package com.example.bookapp.network


import com.example.bookapp.data.Book
import retrofit2.http.*

interface Book2API {

    @GET("books")
    suspend fun getBooks(): MutableList<Book>

    @GET("book")
    suspend fun getBook(
        @Query("id")
        id : Int
    ) : Book

    @POST("books")
    suspend fun addBook(@Body book : Book);

    @POST("books/package")
    suspend fun addBooks(@Body books: List<Book>)

    @PUT("books")
    suspend fun updateBook(@Body book: Book)

    @PUT("books/package")
    suspend fun updateBooks(@Body books: List<Book>)


    @DELETE("books")
    suspend fun deleteBook(
        @Query("id")
        id : Int
    )

    @POST("books/delete")
    suspend fun deleteBooks(
        @Body books: List<Book>
    )

    @GET("books/max")
    suspend fun getMaxBook() : Book

}