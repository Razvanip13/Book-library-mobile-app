package com.example.bookapp.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object BookDetailsScreen: Screen("book_detail_screen")
    object AddBookScreen: Screen("add_book_screen")
    object UpdateBookScreen: Screen("update_book_screen")
    object DeleteBookScreen: Screen("delete_book_screen")
}