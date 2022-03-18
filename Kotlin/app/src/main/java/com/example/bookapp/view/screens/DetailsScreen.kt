package com.example.bookapp.view

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.bookapp.navigation.Screen
import com.example.bookapp.viewmodel.BookModelView
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookViewModel2
import com.example.bookapp.model.repo.BookViewModel2Factory
import com.example.bookapp.util.ConnectionState
import com.example.bookapp.util.connectivityState
import com.example.bookapp.viewmodel.TestViewModel
import com.example.bookapp.viewmodel.TestViewModelFactory


@Composable
fun DetailsScreen(
    navController: NavController,
    testViewModel: TestViewModel,
    id: Long?,
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Modify", "Delete")
    val openDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

//    val bookViewModel2 : BookViewModel2 = viewModel(
//        factory = BookViewModel2Factory(context.applicationContext as Application)
//    )

//    val testViewModel: TestViewModel = viewModel(
//        factory = TestViewModelFactory(context.applicationContext as Application)
//    )

//    val book = id?.let { bookViewModel2.getBook(it) }

//    val book = id?.let { testViewModel.getBook(it) }

    if (id != null) {
        testViewModel.setCurrentBook(id)
    }
    val book = testViewModel.currentBook.value

    Log.d("hash",book.toString())


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
                                  painter = rememberImagePainter(data = book?.image_code),
                                  contentDescription = "",
                                  contentScale = ContentScale.Fit,
                                  modifier = Modifier
                                      .fillMaxWidth()
                                      .padding(bottom = 20.dp)

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

                                      book?.name?.let { it1 ->

                                          Text(
                                              text = it1,
                                              style = TextStyle(
                                                  color = Color.Black,
                                                  fontSize = 33.sp
                                              ),
                                              textAlign = TextAlign.Left,
                                              modifier = Modifier.padding(bottom = 5.dp)
                                          )
                                      }


                                      book?.author?.let { it1 ->
                                          Text(
                                              text = it1,
                                              style = TextStyle(
                                                  color = Color.Black,
                                                  fontSize = 20.sp
                                              ),
                                              textAlign = TextAlign.Left,
                                              modifier = Modifier.padding(bottom = 5.dp)
                                          )
                                      }

                                      Row() {
                                          var i = 0;
                                          while (i < book?.rating!!) {
                                              Icon(
                                                  imageVector = Icons.Filled.Star,
                                                  contentDescription = null,
                                                  tint = Color(0xfffcbe2b),
                                                  modifier = Modifier.size(35.dp)
                                              )
                                              i++
                                          }

                                          i = 0

                                          while (i < 5 - book.rating!!) {
                                              Icon(
                                                  imageVector = Icons.Filled.Star,
                                                  contentDescription = null,
                                                  tint = Color(0xffffffff),
                                                  modifier = Modifier.size(35.dp)
                                              )
                                              i++
                                          }

                                          Text(
                                              text = book.status.toString(),
                                              style = TextStyle(
                                                  color = Color.Black,
                                                  fontSize = 20.sp
                                              ),
                                              modifier = Modifier.padding(start = 90.dp)
                                          )

                                      }
                                      book?.description?.let { it1 ->
                                          Text(
                                              text = it1,
                                              style = TextStyle(
                                                  color = Color.Black,
                                                  fontSize = 20.sp
                                              ),
                                          )
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
                    onClick = {
                        selectedItem = 1
                        navController.navigate(Screen.UpdateBookScreen.route + "/${book?.id}")
                    }
                )



            }
        }
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
                            openDialog.value = false
                            navController.navigate(Screen.MainScreen.route)


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