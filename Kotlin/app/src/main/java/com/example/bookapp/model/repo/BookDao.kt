package com.example.bookapp.model.repo

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.bookapp.data.Book
import kotlinx.coroutines.flow.StateFlow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table WHERE protocol!='Delete'")
    fun getAllBooks() : LiveData<MutableList<Book>>


    @Query("SELECT * FROM book_table WHERE protocol!='Delete'")
    suspend fun getAllBooks2() : List<Book>

    @Query("SELECT * FROM book_table WHERE id = :id")
    suspend fun getById(id: Long) : Book


    @Query("SELECT * FROM book_table WHERE protocol='Add'")
    fun getProtocolAdd(): List<Book>

    @Query("SELECT * FROM book_table WHERE protocol = 'Update'")
    fun getProtocolUpdate() : List<Book>

    @Query("SELECT * FROM book_table WHERE protocol = 'Delete'")
    fun getProtocolDelete() : List<Book>

    @Query("DELETE FROM book_table WHERE protocol = 'Delete'")
    suspend fun removeProtocolDelete()

    @Query("Update book_table SET protocol='Delete' WHERE id=:id")
    suspend fun markForDelete(id: Long)

    @Query("Update book_table SET protocol = 'None' WHERE protocol = 'Add' or protocol = 'Update'")
    suspend fun clearProtocolAddUpdate()

    @Query("Delete From book_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(book: Book)

    @Update
    suspend fun updateBook(book : Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM book_table WHERE id=:id")
    fun getOne(id: Long): LiveData<Book>

    @Insert
    fun addBooks(books: List<Book>)


}