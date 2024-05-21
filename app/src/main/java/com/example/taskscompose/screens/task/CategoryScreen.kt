package com.example.taskscompose.screens.task

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel
) {
    val tags = viewModel.tags.collectAsState()
    Surface {


        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(tags.value.size) {
                Card {
                    Text(text = tags.value[it].name)
                }
            }
        }
    }

}