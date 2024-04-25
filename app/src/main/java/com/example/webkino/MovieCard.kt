package com.example.webkino

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.offWhiteColor

@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp, 10.dp)
            .fillMaxWidth()
            .height(175.dp).
            clickable {
                navController.navigate("movieDetailsScreen/${movie.id}")
            },
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