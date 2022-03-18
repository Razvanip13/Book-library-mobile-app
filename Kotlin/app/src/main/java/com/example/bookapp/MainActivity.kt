package com.example.bookapp

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookStauts
import com.example.bookapp.model.repo.BookRepositoryImpl
import com.example.bookapp.model.repo.BookViewModel2
import com.example.bookapp.model.repo.BookViewModel2Factory
import com.example.bookapp.ui.theme.BookAppTheme
import com.example.bookapp.viewmodel.BookModelView
import com.example.bookapp.viewmodel.BookModelViewFactory
import com.example.bookapp.viewmodel.TestViewModel
import com.example.bookapp.viewmodel.TestViewModelFactory




class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent{
            val context = LocalContext.current
            val  cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

            val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val datac = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            val ethernet = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET)

            var connected = true

            if(wifi!!.isConnected() || datac!!.isConnected() || ethernet!!.isConnected()){
                toast(message = "We are connected")
            }
            else{
//                toast(message = "We are not connected ")
                connected = false
            }

            val testViewModel: TestViewModel = viewModel(
                factory = TestViewModelFactory(context.applicationContext as Application, connected)
            )


            BookAppTheme {

                Surface(color = MaterialTheme.colors.background) {
                    Navigation(testViewModel)
                }
            }


        }
    }
}





@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookAppTheme {
        Greeting("Android")
    }
}