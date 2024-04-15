package com.example.webkino

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor

@Composable
fun GenreItem(genre: Genre) {
    var isChecked by remember { mutableStateOf(genre.isChecked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(end = 8.dp),
            colors = CheckboxDefaults.colors(goldenColor)
        )
        Text(text = genre.name, fontSize = 15.sp, color = darkerGreyColor)
    }

    // Update the isChecked property of the corresponding Genre object
    genre.isChecked = isChecked
}