package com.example.bookapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookProtocol
import com.example.bookapp.data.BookStauts
import com.example.bookapp.navigation.Screen
import com.example.bookapp.viewmodel.TestViewModel


@Composable
fun UpdateScreen (
    navController: NavController,
    testViewModel: TestViewModel,
    id: Long
){
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Modify")

    val context = LocalContext.current
//    val bookViewModel2 : BookViewModel2 = viewModel(
//        factory = BookViewModel2Factory(context.applicationContext as Application)
//    )

    val openDialog = remember { mutableStateOf(false) }



//    bookViewModel2.getBook2(id);
    testViewModel.setCurrentBook(id)

    val book by remember {
//        bookViewModel2.currentBook
        testViewModel.currentBook
    }

    if (book.id == id) {

        var name by remember {
            mutableStateOf(book.name)
        }

        var author by remember {
            mutableStateOf(book.author)
        }
        var description by remember {
            mutableStateOf(book.description)
        }

        var sliderPosition by remember { mutableStateOf(book.rating?.toFloat()) }


        val pictureLink = remember {
            mutableStateOf(book.image_code)
        }


        val (selectedOption, onOptionSelected) = remember { mutableStateOf(book.status.toString()) }

        val radioOptions = listOf("Reading", "Finished", "Hold")


        Scaffold(

            content = {

                Box(


                ) {
                    Column() {

                        Box(
                            modifier = Modifier.height((250.dp)),
                            contentAlignment = Alignment.Center

                        ) {
                            Image(
                                painter = rememberImagePainter(data = pictureLink.value),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)
                                    .clickable { openDialog.value = true }
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black
                                            ),
                                            startY = 500f
                                        )
                                    )
                            )

                        }
                        LazyColumn(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(bottom = 50.dp)
                                ) {
                                    name?.let { it1 ->
                                        OutlinedTextField(
                                            value = it1,
                                            onValueChange = { newText ->
                                            name = newText

                                            },
                                            label = {
                                                Text(text = "Title")
                                            },
                                            maxLines = 1,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }


                                    author?.let { it1 ->
                                        OutlinedTextField(
                                            value = it1,
                                            onValueChange = { newText ->
                                                author = newText
                                            },
                                            label = {
                                                Text(text = "Author")
                                            },
                                            maxLines = 1,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Text("Rating")

                                    sliderPosition?.let { it1 ->
                                        Slider(
                                            value = it1,
                                            onValueChange = { sliderPosition = it },
                                            valueRange = 1f..5f,
                                            steps = 4,
                                        )
                                    }


                                    radioOptions.forEach { text ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(30.dp)
                                                .selectable(
                                                    selected = (text == selectedOption),
                                                    onClick = { onOptionSelected(text) },
                                                    role = Role.RadioButton
                                                )
                                                .padding(horizontal = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = (text == selectedOption),
                                                onClick = null // null recommended for accessibility with screenreaders
                                            )
                                            Text(
                                                text = text,
                                                style = MaterialTheme.typography.body1.merge(),
                                                modifier = Modifier.padding(start = 16.dp)
                                            )
                                        }
                                    }

                                    description?.let { it1 ->
                                        OutlinedTextField(
                                            value = it1,
                                            onValueChange = { newText ->
                                                description = newText
                                            },
                                            label = {
                                                Text(text = "Description")
                                            },
                                            maxLines = 6,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                        )
                                    }
                                    Button(onClick = {
                                        val updatedBook = Book(
                                            id,
                                            name,
                                            author,
                                            BookStauts.valueOf(selectedOption),
                                            description,
                                            sliderPosition?.toLong(),
                                            pictureLink.value,
                                            BookProtocol.None
                                        )

                                        if (!testViewModel.isConnected()){
                                            updatedBook.protocol = BookProtocol.Update
                                        }
//                                        bookViewModel2.updateBook(updatedBook)
                                        testViewModel.updateBook(updatedBook)
                                        navController.navigate(Screen.BookDetailsScreen.route + "/${id}")
                                    }) {
                                        Text("Save")
                                    }
                                }

                            }
                        }

                    }
                }
            },

            bottomBar = {

                BottomNavigation(
                    elevation = BottomNavigationDefaults.Elevation,
                ) {

                    selectedItem = 1

                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(items[0]) },
                        selected = selectedItem == 0,
                        onClick = {
                            selectedItem = 0
                            navController.navigate(Screen.MainScreen.route)
                        }
                    )

                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Edit, contentDescription = null) },
                        label = { Text(items[1]) },
                        selected = selectedItem == 1,
                        onClick = { selectedItem = 1 }
                    )


                }
            }
        )


        if (openDialog.value) {
            var link by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Put the link for the image")
                },
                text = {
                    OutlinedTextField(
                        value = link,
                        onValueChange = { newText ->
                            link = newText
                        },
                        maxLines = 4
                    )
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(all = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(0.3f),
                            onClick = {
                                pictureLink.value = link
                                openDialog.value = false
                            }
                        ) {
                            Text("Yes")
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            onClick = { openDialog.value = false }
                        ) {
                            Text("No")
                        }
                    }
                }
            )
        }
    }

}