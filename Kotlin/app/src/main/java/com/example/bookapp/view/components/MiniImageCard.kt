package com.example.bookapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.bookapp.data.Book

@Composable
fun MiniImageCard(
    contentDescripition: String,
    book: Book,
    modifier: Modifier = Modifier
){
    Box() {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp


        ) {
            Column() {
                Box(
                    modifier = Modifier.height((180.dp)),
                    contentAlignment = Alignment.Center

                ) {

                    Image(
                        painter =  rememberImagePainter(data = book.image_code),
                        contentDescription = contentDescripition,
                        contentScale = ContentScale.Fit
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
                                    startY = 350f
                                )
                            )
                    )

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {

                    Row(modifier = Modifier.fillMaxWidth()){
                        Column(

                            horizontalAlignment = Alignment.Start
                        ) {
                            book.name?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(color = Color.Black, fontSize = 20.sp),
                                    textAlign = TextAlign.Left
                                )
                            }

                            Row() {
                                var i=0;
                                while(i < book.rating!!){
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = Color(0xfffcbe2b),
                                        modifier = Modifier.height(20.dp)
                                    )
                                    i++
                                }

                                i = 0

                                while(i < 5 - book.rating!!){
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = Color(0xffffffff),
                                        modifier = Modifier.height(20.dp)
                                    )
                                    i++
                                }

                            }
                        }

                    }

                }
            }


        }
    }
}