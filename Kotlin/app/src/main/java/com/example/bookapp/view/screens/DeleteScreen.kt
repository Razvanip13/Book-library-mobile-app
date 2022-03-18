package com.example.bookapp.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookProtocol
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookViewModel2
import com.example.bookapp.model.repo.BookViewModel2Factory
import com.example.bookapp.util.ConnectionState
import com.example.bookapp.util.connectivityState
import com.example.bookapp.viewmodel.BookModelView
import com.example.bookapp.viewmodel.TestViewModel
import com.example.bookapp.viewmodel.TestViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun DeleteScreen(
    navController: NavController,
    testViewModel: TestViewModel
) {

    fun deleteThatBook(id :Long){

        Log.d("we","merge")

    }

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Add", "Modify")
    val openDialog = remember { mutableStateOf(false) }
    val isDeleted = remember{ mutableStateOf(false)}

    val id = remember{ mutableStateOf(0L)}
    val the_book = remember {
        Book(0,"","",BookStauts.Hold,"",0,"", BookProtocol.Delete)
    }

    val context = LocalContext.current

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

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


    val items2 = testViewModel.bookData



    Scaffold(

        content = {


            Box(


            ) {
                LazyColumn {
                    itemsIndexed(
//                        listOfBooks.value
//                        items2.value
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
                                        the_book.id = book.id;
                                        Log.d("serpos", the_book.toString())
                                        id.value = book.id!!

//                                        deleteThatBook(id)
                                        openDialog.value = true
                                        isDeleted.value = false

                                    },
                                contentAlignment = Alignment.Center,

                                ) {
                                Column() {
                                    Column() {
                                        MiniImageCard(
                                            contentDescripition = "",
                                            book = book,

                                        )
                                    }

                                }
                            }

                    }
                }
            }


            if(isDeleted.value){

                Log.d("serpos",the_book.toString())
//
//               bookViewModel2.deleteBook(the_book)
                testViewModel.deleteBook(the_book)
                isDeleted.value = false
            }


        },
    )

    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            text = {
                Text(text = "Sure you want to delete this book?")
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        onClick = {
                            Log.d("we","merge")

                            openDialog.value = false
                            isDeleted.value =true

                        }
                    ) {
                        Text("Yes")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onClick = { openDialog.value = false }
                    ) {
                        Text("No")
                    }
                }
            }
        )
    }

}