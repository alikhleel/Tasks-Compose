package com.example.taskscompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.ui.theme.Navy


@Composable
fun TasksHeaderView(
    modifier: Modifier = Modifier, title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .weight(0.18f)
                .padding(8.dp)
                .clickable {
                    onBackClick()
                },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
        }
        Text(
            title,
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .padding(end = 60.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Navy,
            textAlign = TextAlign.Center

        )
    }

}