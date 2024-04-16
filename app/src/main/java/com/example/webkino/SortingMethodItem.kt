package com.example.webkino

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.webkino.ui.theme.darkGreyColor
import com.example.webkino.ui.theme.darkerGreyColor
import com.example.webkino.ui.theme.goldenColor
import com.example.webkino.ui.theme.offWhiteColor

@Composable
fun SortingMethodsLazyColumn(
    sortingMethods: List<SortingMethod>
) {
//    LazyColumn(
//        modifier = Modifier
//            .height(320.dp)
//            .fillMaxWidth()
//    ) {
//        items(temporarySortingMethods) { sortingMethod ->
//            var isSelected by remember { mutableStateOf(sortingMethod.isSelected) }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                    .background(color = if (isSelected) darkGreyColor else offWhiteColor)
//                    .clickable {
//                        temporarySortingMethods.forEach { it.isSelected = false }
//                        sortingMethod.isSelected = true
//                        isSelected = true
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = sortingMethod.name,
//                    color = if (isSelected) goldenColor else darkerGreyColor,
//                    fontSize = 15.sp,
//                    modifier = Modifier.padding(8.dp)
//                )
//            }
//        }
//    }
}