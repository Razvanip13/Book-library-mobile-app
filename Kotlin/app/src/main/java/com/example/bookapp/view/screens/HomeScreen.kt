package com.example.bookapp.view

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookapp.navigation.Screen
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookViewModel2
import com.example.bookapp.model.repo.BookViewModel2Factory
import com.example.bookapp.viewmodel.*
import android.net.NetworkInfo
import android.widget.Toast
import com.example.bookapp.util.ConnectionState
import com.example.bookapp.util.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(navController: NavController, testViewModel: TestViewModel) {


    fun navigateToBookDescriptions(book: Book) {
        navController.navigate(Screen.BookDetailsScreen.route + "/${book.id}")
    }


    val items2 = testViewModel.bookData


    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available




    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Add", "Remove")


    Scaffold(

        content = {

            Box(
                modifier = Modifier
                    .padding(bottom=50.dp)

            ) {
                LazyColumn {
                    itemsIndexed(
                        //listOfMovies

                       items2.value
                    ) { index, book ->

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                .padding(
                                    start = 30.dp,
                                    top = 30.dp,
                                    end = 30.dp,
                                    bottom = 30.dp
                                )
                                .clickable {
                                    navigateToBookDescriptions(book)
                                },
                            contentAlignment = Alignment.Center,

                            ) {
                            ImageCard(
                                contentDescripition = "",
                                book = book
                            )
                        }
                    }
                }
            }


        },


        bottomBar = {

            BottomNavigation(

                elevation = BottomNavigationDefaults.Elevation,

                ) {


                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text(items[0]) },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                    }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    label = { Text(items[1]) },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate(Screen.AddBookScreen.route)
                    }
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
                    label = { Text(items[2]) },
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        navController.navigate(Screen.DeleteBookScreen.route)
                    }
                )


            }

        }
    )

    if(!isConnected){

        if(testViewModel.isConnected() == true) {
            testViewModel.setAllBooksLocal()
            Toast.makeText(LocalContext.current, "Disconnected", Toast.LENGTH_SHORT).show()
        }

    }
    else{
        if(testViewModel.isConnected() == false) {
            testViewModel.setAllBooksRemote()
            Toast.makeText(LocalContext.current, "Connected", Toast.LENGTH_SHORT).show()
        }

    }

}

