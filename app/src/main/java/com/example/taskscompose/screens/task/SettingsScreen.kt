package com.example.taskscompose.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.taskscompose.components.TasksHeaderView
import com.example.taskscompose.ui.theme.PrimaryColor


@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    var isLanguageDialogOpen by rememberSaveable { mutableStateOf(false) }
    var selectedLanguage by rememberSaveable { mutableStateOf("English") }

    if (isLanguageDialogOpen) {
        LanguageDialog(
            selectedLanguage = selectedLanguage,
            languages = listOf("English", "Arabic"),
            onLanguageSelected = { language ->
                selectedLanguage = language
                isLanguageDialogOpen = false
            },
            onDismissRequest = { isLanguageDialogOpen = false }
        )
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TasksHeaderView(title = "Settings") {
            navController.popBackStack()
        }

        LazyColumn {

            item {
                Column {

                    Text(
                        "General",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = PrimaryColor
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Language",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "English",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = PrimaryColor
                        )
                        IconButton(onClick = { isLanguageDialogOpen = true }) {
                            Icon(
                                Icons.Outlined.KeyboardArrowRight,
                                contentDescription = "select language",
                                tint = PrimaryColor
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun LanguageDialog(
    modifier: Modifier = Modifier,
    selectedLanguage: String = "English",
    languages: List<String> = listOf("English", "Arabic"),
    onLanguageSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Language", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryColor
                )
                Spacer(modifier = Modifier.height(32.dp))
                for (language in languages) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                onLanguageSelected(language)
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            language,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (language == selectedLanguage) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .background(PrimaryColor)
                                    .padding(4.dp)
                            ) {

                                Icon(
                                    Icons.Outlined.Check,
                                    contentDescription = "selected language",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun LanguageDialogPreview() {
    LanguageDialog(languages = listOf("English", "Arabic"),
        onLanguageSelected = {},
        onDismissRequest = {})
}