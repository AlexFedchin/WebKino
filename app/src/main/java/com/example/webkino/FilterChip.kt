package com.example.webkino

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor

@Composable
fun FilterChip(text: String, onClick: () -> Unit, image: Painter) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = goldenColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .width(110.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Image(
                painter = image,
                contentDescription = stringResource(R.string.filter_chip),
                colorFilter = ColorFilter.tint(darkerGreyColor),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = darkerGreyColor, fontSize = 15.sp)
        }
    }
}