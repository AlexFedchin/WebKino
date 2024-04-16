package com.example.webkino

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.offWhiteColor

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(175.dp),
        colors = CardDefaults.cardColors(offWhiteColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the image if loaded successfully
            movie.poster_image?.let {
                Image(
                    bitmap = it,
                    contentDescription = movie.title,
                    modifier = Modifier.height(130.dp))
            }

            if (movie.poster_image == null) {
                Image(
                    painter = painterResource(id = R.drawable.poster_not_found),
                    contentDescription = movie.title,
                    modifier = Modifier.height(130.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = movie.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                )
                Text(
                    text = if (movie.release_date.isNotEmpty()) {
                        "(${movie.release_date.substring(0, 4)})"
                    } else {
                        "(Unknown)"
                    },
                    style = TextStyle(fontWeight = FontWeight.Normal, color = darkGreyColor),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = movie.overview,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}



//@Preview
//@Composable
//fun MovieCardPreview() {
//    MovieCard(movie = Movie(
//        adult = false,
//        backdrop_path = "/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg",
//        genre_ids = listOf(878, 12),
//        id = 693134,
//        original_language = "en",
//        original_title = "Dune: Part Two",
//        overview = "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.",
//        popularity = 4651.845,
//        poster_path = "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
//        release_date = "2024-02-27",
//        title = "Dune: Part Two",
//        video = false,
//        vote_average= 8.316,
//        vote_count = 2676
//        poster_image =
//    ))
//}
