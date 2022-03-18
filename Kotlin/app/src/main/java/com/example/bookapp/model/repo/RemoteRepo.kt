package com.example.bookapp.model.repo

import com.example.bookapp.network.RetrofitInstance
import com.example.bookapp.data.Book

class RemoteRepo {

    suspend fun getBook(id : Int): Book {

        return RetrofitInstance.api.getBook(id)
    }

    suspend fun getBooks() :MutableList<Book>{
        return RetrofitInstance.api.getBooks()
    }

    suspend fun deleteBook(id: Int){
        RetrofitInstance.api.deleteBook(id)
    }

    suspend fun addBook(book: Book){
        RetrofitInstance.api.addBook(book)
    }

    suspend fun updateBook(book:Book){
        RetrofitInstance.api.updateBook(book)
    }

    suspend fun getMaxIdBook() :Book{
        return RetrofitInstance.api.getMaxBook();
    }

    suspend fun deleteBooks(books : List<Book>){
        RetrofitInstance.api.deleteBooks(books)
    }


    suspend fun addBooks(books : List<Book>){
        RetrofitInstance.api.addBooks(books)
    }

    suspend fun updateBooks(books : List<Book>){
        RetrofitInstance.api.updateBooks(books)
    }
}