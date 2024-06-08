package com.example.assignment_1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "movieList") {
                    composable("movieList") {
                        MovieList(navController, movies, viewModel)
                    }
                    composable(
                        route = "movieDetails/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieId")
                        Log.i("TAG", "movieId: $movieId")
                        MovieDetails(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(result: Result, navController: NavController, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(280.dp)
            .clickable {
                viewModel.selectMovie(result)
                navController.navigate("movieDetails/${result.id}")
            }
    ) {
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = { "https://image.tmdb.org/t/p/w92/${result.posterPath}" },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Text(
            text = result.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = result.releaseDate,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp, start = 5.dp, end = 5.dp)
        )
    }
}

@Composable
fun MovieDetails(viewModel: MainViewModel) {

    val movie by viewModel.selectedMovie.collectAsState()
    Log.i("TAG", "movieDetails: $movie")

    movie?.let {
        LazyColumn(
            modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)
        ) {
            item {
                Column {
                    com.skydoves.landscapist.glide.GlideImage(
                        imageModel = { "https://image.tmdb.org/t/p/w92/${it.posterPath}" },
                        modifier = Modifier
                            .height(500.dp)
                            .width(400.dp)
                    )
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Row {
                        Text(
                            text = "Language: ",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = it.originalLanguage,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row {
                        Text(
                            text = "Release Date: ",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = it.releaseDate,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row {
                        Text(
                            text = "Average Votes: ",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${it.voteAverage}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Column {
                        Text(
                            text = "OverView",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = it.overview,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieList(navController: NavController, results: List<Result>, viewModel: MainViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(results) {
            MovieCard(result = it, navController = navController, viewModel = viewModel)
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
fun MyApp(names: List<String>) {
//    Surface(color = darkColorScheme().primary) {
    Column(modifier = Modifier.padding(10.dp, 5.dp)) {
        Greeting("Android Assignment")
        for (name in names) {
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