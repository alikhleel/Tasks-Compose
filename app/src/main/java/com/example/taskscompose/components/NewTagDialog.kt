package com.example.taskscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.getAllColors
import com.example.taskscompose.getAllSystemIcons
import com.example.taskscompose.ui.theme.LightGray
import com.example.taskscompose.ui.theme.PrimaryColor


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewTagDialog(
    modifier: Modifier = Modifier, onConfirm: (Tags) -> Unit, onDismiss: () -> Unit
) {

    val colors = getAllColors()
    val icons = getAllSystemIcons().toList()


    var tagName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(colors[0]) }
    var selectedIcon by remember { mutableStateOf(icons[0]) }


    BaseDialog(
        modifier = Modifier.padding(16.dp), title = "New Tag", onConfirm = {
            val tag = Tags(
                name = tagName, color = selectedColor.toArgb().toString(), icon = selectedIcon.name
            )
            onConfirm(tag)

        }, onDismiss = onDismiss
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightGray,
                    focusedIndicatorColor = LightGray.copy(alpha = 0.5f),
                ), placeholder = { Text("Tag Name") }, value = tagName, onValueChange = {
                    tagName = it
                })
                Spacer(modifier = Modifier.padding(16.dp))

                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    colors.forEach { color ->
                        Box(modifier = Modifier
                            .size(60.dp)
                            .padding(4.dp)
                            .background(
                                color = color, shape = CircleShape
                            )
                            .clickable {
                                selectedColor = color
                            }) {
                            if (selectedColor == color) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(16.dp))
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Start
                ) {
                    icons.forEach { icon ->
                        Box(modifier = Modifier
                            .size(60.dp)
                            .padding(8.dp)
                            .background(
                                color = if (selectedIcon == icon) PrimaryColor
                                else LightGray, shape = CircleShape
                            )
                            .clickable {
                                selectedIcon = icon
                            }) {
                            Icon(
                                icon, contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }
}
