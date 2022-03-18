package com.example.bookapp.data

import androidx.compose.runtime.MutableState
import androidx.room.PrimaryKey
import androidx.room.Entity

enum class BookStauts {
    Finished, Reading, Hold
}

enum class BookProtocol{
    None, Add, Delete, Update
}

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var name: String?,
    var author: String?,
    var status: BookStauts?,
    var description: String?,
    var rating: Long?,
    var image_code: String?,
    var protocol: BookProtocol
)