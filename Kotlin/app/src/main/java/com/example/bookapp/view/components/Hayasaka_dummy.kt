package com.example.bookapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.bookapp.R

@Composable
fun Hayasaka_dummy() {
        Column(){
        Image(
            bitmap = ImageBitmap.imageResource(
                id = R.drawable.ai
            ),
            contentDescription = "I'm just learning, don't judge me",
            modifier = Modifier.height(300.dp),
            contentScale =  ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(16.dp),

        ) {
            Text(text = "Ai Hayasaka")
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "100 percent quality waifu")
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "$99999.99")
        }
    }

}