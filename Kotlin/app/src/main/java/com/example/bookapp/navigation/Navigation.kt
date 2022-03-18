package com.example.bookapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookapp.view.*
import com.example.bookapp.navigation.Screen
import com.example.bookapp.viewmodel.BookModelView
import com.example.bookapp.viewmodel.TestViewModel

@Composable
fun Navigation(model: TestViewModel){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ){
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController, testViewModel = model)
        }
        composable(
            route = Screen.DetailScreen.route
        ){
            Hayasaka_dummy()
        }
        composable(
            route = Screen.BookDetailsScreen.route +"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong("id")
            DetailsScreen(navController = navController,testViewModel = model , id = id)

        }

        composable(
            route = Screen.AddBookScreen.route
        ){
            AddScreen(navController = navController, testViewModel = model)
        }

        composable(
            route = Screen.UpdateBookScreen.route +"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                }
            )
        ){
            val id = it.arguments?.getLong("id")
            if (id != null) {
                UpdateScreen(navController = navController, testViewModel = model, id = id)
            }
        }

        composable(
            route = Screen.DeleteBookScreen.route
        ){
            DeleteScreen(navController = navController, testViewModel = model)
        }
    }
}
