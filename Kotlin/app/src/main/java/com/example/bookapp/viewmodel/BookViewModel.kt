package com.example.bookapp.model.repo

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookProtocol
import com.example.bookapp.data.BookStauts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class BookViewModel2(application: Application): AndroidViewModel(application) {

    val bookData : LiveData<MutableList<Book>>
    private val repository: LocalRepo
    private var _currentBook : MutableState<Book> =
        mutableStateOf(Book(0,"","",BookStauts.Hold,"",0,"", BookProtocol.None))
    val currentBook = _currentBook


    init {
        val bookDao = BookDataBase.getDatabase(application).bookDao()
        repository = LocalRepo(bookDao = bookDao)
        bookData = repository.getBooks()

    }

    fun getBook2(id: Long){
        viewModelScope.launch {


            val book  = repository.getBook(id)

            if(book.id!=null){
                _currentBook.value = repository.getBook(id)
            }
            Log.d("pirpipr" , "Am rulat")
        }
    }

    fun getBook(id: Long) = runBlocking { repository.getBook(id) }

    fun addBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }

    fun getOne(id : Long) : LiveData<Book>{
        return repository.getOne(id)
    }

}

class BookViewModel2Factory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(BookViewModel2::class.java)){
            return BookViewModel2(application = application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}