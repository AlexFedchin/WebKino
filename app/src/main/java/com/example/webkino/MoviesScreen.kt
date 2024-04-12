package com.example.webkino

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.navigation.compose.rememberNavController
import com.example.webkino.ui.theme.bgGradient
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.offWhiteColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

// Define your API key here
private const val API_KEY = "abf3a6ac8c9d2e33c91d318ea187ea19"

// Define the base URL of the API
private const val BASE_URL = "https://api.themoviedb.org/3/"

// Define a Retrofit interface for making API calls
interface MovieApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1, // Default page is 1
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc" // Default sorting
    ): Call<MovieResponse>
}

// Define data classes to represent API response
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var poster_image: ImageBitmap? // Add this field for storing the poster image
)



@Composable
fun MoviesScreen(navController: NavHostController) {
    // Create a Retrofit instance
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create an instance of the API service
    val service = retrofit.create(MovieApiService::class.java)

    // Define a state for holding the list of movies
    val moviesState = remember { mutableStateOf<List<Movie>>(emptyList()) }

    // Function for fetching movie data
    service.getMovies().enqueue(object : Callback<MovieResponse> {
        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            if (response.isSuccessful) {
                println("Movie data fetched")
                val movies = response.body()?.results ?: emptyList()
                CoroutineScope(Dispatchers.IO).launch {
                    fetchPosterImagesForMovies(movies)
                    withContext(Dispatchers.Main) {
                        moviesState.value = movies
                    }
                }
            }
        }

        private suspend fun fetchPosterImagesForMovies(movies: List<Movie>) {
            // Iterate through each movie and fetch its poster image
            movies.forEach { movie ->
                try {
                    val inputStream = URL("https://image.tmdb.org/t/p/w500${movie.poster_path}").openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val imageBitmap = bitmap.asImageBitmap()
                    movie.poster_image = imageBitmap
                    println("Images fetched and recorded")
                } catch (e: Exception) {
                    println(e)
                }
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            // Error handling
        }
    })

    Box(modifier = Modifier
        .background(brush = bgGradient)
        .fillMaxSize())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        // Display the list of movies using LazyColumn
        LazyColumn(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
        ) {
            items(moviesState.value) { movie ->
                MovieCard(movie = movie)
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        // Button to switch to the MoviesScreen
        Button(
            onClick = {
                navController.navigate("homeScreen")
            },
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = offWhiteColor),
        ) {
            Text(
                "Back",
                fontSize = 18.sp,
                color = darkGreyColor
            )
        }
    }
}