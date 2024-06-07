package com.example.assignment_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment_1.data.model.Result
import com.example.assignment_1.ui.theme.Assignment_1Theme

class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels() //fetch viewModel from factory of type MainViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel.fetchMovies()
        
        enableEdgeToEdge()
//        val namesList = listOf("World", "Compose")
        setContent {
            Assignment_1Theme {
//                MyApp(namesList)
                val movies by viewModel.movies.collectAsState()
                MovieList(results = movies)
            }
        }
    }
}


@Composable
fun MovieCard(result: Result) {
    Card (
      modifier = Modifier
          .padding(16.dp, 5.dp)
          .fillMaxWidth()
    ){
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = { "https://image.tmdb.org/t/p/w92/${result.posterPath}" },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MovieList(results: List<Result>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(results){
            MovieCard(result = it)
        }
    }
}

//Unused but for learning

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment_1Theme {
        val namesList = listOf("World", "Compose")
        MyApp(namesList)
    }
}

@Composable
fun MyApp(names: List<String> ) {
//    Surface(color = darkColorScheme().primary) {
    Column (modifier = Modifier.padding(10.dp, 5.dp)) {
        Greeting("Android Assignment")
        for(name in names) {
            TestColsAndRows(name)
        }
    }
//    }
}

@Composable
fun TestColsAndRows(name: String) {
    Text(text = "Hello")
    Text(text = name)
}

@Composable
fun Greeting(name: String) {
    Column {
        Text(
            text = "I'm testing Column",
            modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp),
            fontFamily = FontFamily.Serif,
        )
        Text(
            text = "Welcome to the $name!",
            fontFamily = FontFamily.Default
        )
    }
}