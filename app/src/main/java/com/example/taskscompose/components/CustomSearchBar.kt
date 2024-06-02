package com.example.taskscompose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskscompose.ui.theme.LightGray
import com.example.taskscompose.ui.theme.LightPrimary

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
    onClearClick: () -> Unit,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(modifier = modifier
        .clip(MaterialTheme.shapes.medium)
        .fillMaxWidth(),
        value = query,
        onValueChange = { onQueryChange(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightGray,
            unfocusedContainerColor = LightGray,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        leadingIcon = {
            IconButton(onClick = { onSearchClick(query) }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = LightPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        placeholder = {
            Text("Search for task")
        },
        trailingIcon = {
            IconButton(onClick = { onClearClick() }) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Clear",
                    tint = LightPrimary,
                    modifier = Modifier.size(20.dp)
                )

            }
        })
}
