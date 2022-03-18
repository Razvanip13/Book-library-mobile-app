package com.example.bookapp.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookProtocol
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookViewModel2
import com.example.bookapp.model.repo.BookViewModel2Factory
import com.example.bookapp.navigation.Screen
import com.example.bookapp.viewmodel.BookModelView
import com.example.bookapp.viewmodel.TestViewModel
import com.example.bookapp.viewmodel.TestViewModelFactory


@Composable
fun AddScreen(
    navController: NavController,
    testViewModel: TestViewModel
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Add")
    var name by remember {
        mutableStateOf("")
    }
    var author by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var sliderPosition by remember { mutableStateOf(1f) }

    val openDialog = remember { mutableStateOf(false) }

    val pictureLink = remember {
        mutableStateOf("https://i.pinimg.com/564x/7d/58/48/7d58481d067e336a6338b7bebfc0fe10.jpg")
    }

    var expanded by remember { mutableStateOf(false) }

    val radioOptions = listOf("Reading", "Finished", "Hold")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    val context = LocalContext.current
//    val bookViewModel2 : BookViewModel2 = viewModel(
//        factory = BookViewModel2Factory(context.applicationContext as Application)
//    )


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
                                    .padding(bottom=50.dp)
                            ) {

                                OutlinedTextField(
                                    value = name,
                                    onValueChange = {newText ->
                                        name = newText
                                    },
                                    label ={
                                        Text(text = "Title")
                                    },
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = author,
                                    onValueChange = {newText ->
                                        author = newText
                                    },
                                    label ={
                                        Text(text = "Author")
                                    },
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text("Rating")
                                Slider(
                                    value = sliderPosition,
                                    onValueChange = { sliderPosition = it },
                                    valueRange = 1f..5f,
                                    steps = 4,
                                )

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

                                OutlinedTextField(
                                    value = description,
                                    onValueChange = {newText ->
                                        description = newText
                                    },
                                    label ={
                                        Text(text = "Description")
                                    },
                                    maxLines = 6,
                                    modifier = Modifier.fillMaxWidth()
                                        .height(200.dp)
                                )
                                Button(onClick = {
                                    val book = Book(
                                        id = null,
                                        name = name,
                                        author = author,
                                        status = BookStauts.valueOf(selectedOption),
                                        description = description,
                                        rating = sliderPosition.toLong(),
                                        image_code = pictureLink.value,
                                        protocol = BookProtocol.None
                                    )

                                    if(!testViewModel.isConnected()){
                                        book.protocol = BookProtocol.Add
                                    }
//                                    bookViewModel2.addBook(book)
                                    testViewModel.addBook(book)
                                    navController.navigate(Screen.MainScreen.route)
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
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    label = { Text(items[1]) },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 }
                )

            }
        }
    )


    if(openDialog.value) {
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
                    onValueChange = {newText ->
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
//                            pictureLink.value = link
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