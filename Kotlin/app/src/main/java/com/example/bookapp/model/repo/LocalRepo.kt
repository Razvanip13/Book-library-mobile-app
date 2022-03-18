package com.example.bookapp.model.repo

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.example.bookapp.data.Book
import kotlinx.coroutines.flow.StateFlow


class LocalRepo(private val bookDao: BookDao) {

    fun getBooks() : LiveData<MutableList<Book>> {
        return bookDao.getAllBooks();
    }

    suspend fun getBooks2() : List<Book>{
        return bookDao.getAllBooks2()
    }


    fun getProtocolAdd() : List<Book>{
        return bookDao.getProtocolAdd()
    }

    fun getProtocolUpdate() : List<Book>{
        return bookDao.getProtocolUpdate()
    }

    fun getProtocolDelete() : List<Book>{
        return bookDao.getProtocolDelete()
    }

    suspend fun removeProtocolDelete() {
        bookDao.removeProtocolDelete()
    }

    suspend fun clearProtocolAddUpdate() {
        bookDao.clearProtocolAddUpdate()
    }

    suspend fun markForDelete(id: Long){
        bookDao.markForDelete(id)
    }


    suspend fun addBook(book: Book){
        bookDao.addBook(book)
    }

    suspend fun updateBook(book:Book){
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book){
        bookDao.deleteBook(book)
    }

    suspend fun getBook(id : Long) : Book{
        return bookDao.getById(id)
    }

    fun getOne(id : Long) : LiveData<Book>{
        return bookDao.getOne(id)
    }

    suspend fun deleteAll(){
        bookDao.deleteAll()
    }

    suspend fun addAll(book:List<Book>){
        bookDao.addBooks(book)
    }


}