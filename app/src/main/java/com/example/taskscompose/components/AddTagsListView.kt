package com.example.taskscompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.ui.theme.PrimaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddTagsListView(list: List<Tags>, value: String, onTagClick: (Tags) -> Unit) {
    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            text = "Tags", modifier = Modifier.padding(5.dp), color = Color.Black
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            list.forEach {
                val color = Color(it.color.toInt())
                Card(
                    Modifier
                        .padding(vertical = 2.dp, horizontal = 8.dp)
                        .clickable {
                            onTagClick.invoke(it)
                        }, colors =
                    // if index is equal to the index of the tag, set the color to PrimaryColor
                    if (value == it.name) {
                        CardDefaults.cardColors(
                            containerColor = color,
                            contentColor = Color.White
                        )
                    } else {
                        CardDefaults.cardColors(
                            containerColor = color.copy(alpha = 0.2f),
                            contentColor = color
                        )
                    }
                ) {
                    Text(
                        text = it.name,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }

        }

        Text(
            text = "+ Add new tag", modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable {
                    //todo
                }, color = PrimaryColor, textAlign = TextAlign.Center
        )

    }
}