package com.example.taskscompose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    textColor: Color,
    value: MutableState<String>,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        Text(
            label,
            Modifier.padding(end = 20.dp, start = 20.dp),
            style = TextStyle(color = textColor, fontSize = 16.sp)
        )
        TextField(
            value = value.value,
            onValueChange = { value.value = it },
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AlertDialogDefaults.containerColor,
                unfocusedContainerColor = AlertDialogDefaults.containerColor,
                disabledContainerColor = AlertDialogDefaults.containerColor,
                cursorColor = textColor,
                focusedIndicatorColor = textColor,
                focusedLabelColor = textColor,
            ),
            readOnly = trailingIcon != null,
            trailingIcon = { trailingIcon?.invoke() },
        )
    }

}
