package com.example.webkino

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.lightGreyColor

@Composable
fun RatingStars(rating: Double) {
    val maxStars = 5
    val filledStars = rating.toInt() / 2
    val hasHalfStar = rating - filledStars * 2 >= 1.0

    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(filledStars) {
            Icon(
                painter = painterResource(id = R.drawable.star_filled),
                contentDescription = "Filled Star",
                tint = goldenColor,
                modifier = Modifier.size(24.dp).padding(0.dp, 0.dp, 4.dp)
            )
        }

        if (hasHalfStar) {
            Icon(
                painter = painterResource(id = R.drawable.star_half),
                contentDescription = "Half-Filled Star",
                tint = goldenColor,
                modifier = Modifier.size(24.dp).padding(0.dp, 0.dp, 4.dp)
            )
        }

        repeat(maxStars - filledStars - if (hasHalfStar) 1 else 0) {
            Icon(
                painter = painterResource(id = R.drawable.star_outlined),
                contentDescription = "Outline Star",
                tint = goldenColor,
                modifier = Modifier.size(24.dp).padding(0.dp, 0.dp, 4.dp)
            )
        }

        Text(
            text = "(${String.format("%.1f", rating / 2)})",
            fontSize = 18.sp,
            color = lightGreyColor,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}