package com.example.taskscompose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.ui.theme.PrimaryColor

@Composable
fun FormCreateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                testTag = "AddTaskButton"
            },

        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor
        )

    ) {
        Text(
            text = "Create",
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 16.sp,
            color = Color.White
        )
    }
}