package com.example.bookapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.bookapp.data.Book
import com.example.bookapp.model.repo.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookModelView constructor(
    private val repository: BookRepository
) : ViewModel() {
    private val _listOfBooks: MutableState<List<Book>> = mutableStateOf(emptyList())
    val bookList: State<List<Book>> get() = _listOfBooks

//    private val _listOfBooks: MutableStateFlow<List<Book>>

    init {
        viewModelScope.launch {
            val result = repository.getAllBooks()
            _listOfBooks.value = result
        }
    }

    fun addBook(book: Book) : Long {

        var max = 0L
        val originalData = _listOfBooks.value
        val array = ArrayList<Book>()


        originalData.forEach { x->

            if(x.id!! > max){
                max = x.id!!
            }
        }

        book.id = max + 1

        Log.d("id", book.id.toString())

        array.addAll(originalData)
        array.add(book)
        _listOfBooks.value = array
        return max + 1
    }

    fun deleteBook(id: Long) {

        val array = ArrayList<Book>(_listOfBooks.value)
        array.removeIf { x -> x.id == id }
        _listOfBooks.value = array
        repository.delete(id)
    }

    fun updateBook(book: Book) {


        val originalData = _listOfBooks.value
        val array = ArrayList<Book>()
        array.addAll(originalData)

        for(i in array.indices){
            if(array[i].id == book.id){
                array.set(i,book)
                break
            }

        }

        Log.d("found",array.toString())

        _listOfBooks.value =array

    }

    fun getBook(id : Long) : Book? {
        val array = ArrayList<Book>(_listOfBooks.value)
        return  array.find { x -> x.id == id }
    }
}

class BookModelViewFactory(val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BookRepository::class.java)
            .newInstance(repository)
    }

}

