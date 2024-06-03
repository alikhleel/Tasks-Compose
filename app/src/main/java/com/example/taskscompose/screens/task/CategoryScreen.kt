package com.example.taskscompose.screens.task

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.taskscompose.R
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.getIconName
import com.example.taskscompose.iconByName
import com.example.taskscompose.navigation.Screens
import com.example.taskscompose.ui.theme.DarkGray
import com.example.taskscompose.ui.theme.PrimaryColor
import com.example.taskscompose.utils.ColorUtils
import com.google.firebase.auth.FirebaseUser

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    user: FirebaseUser?,
    onLogout: (Context) -> Unit,
    viewModel: CategoryViewModel,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.getAllTags()
    }

    val tags = viewModel.tags.collectAsState(null)
    var dropDownExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp, bottom = 100.dp),
        columns = GridCells.Fixed(2)
    ) {
        if (user != null) {
            val photo = user.photoUrl
            val name = user.displayName
            val email = user.email
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = { dropDownExpanded = !dropDownExpanded }) {
                        Icon(
                            painter = painterResource(id = R.drawable.more_square),
                            contentDescription = "more options"
                        )
                        DropdownMenu(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(Color.White),
                            expanded = dropDownExpanded,
                            onDismissRequest = { dropDownExpanded = !dropDownExpanded }) {
                            DropdownMenuItem(
                                text = { Text(text = "Settings") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = "settings"
                                    )
                                },
                                onClick = { navController.navigate(Screens.MainApp.SettingsScreen.route) },
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Log Out") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.ExitToApp,
                                        contentDescription = "log out"
                                    )
                                },
                                onClick = { onLogout(context) },
                            )
                        }
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfilePhoto(photo)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(name ?: "User Name")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(email ?: "User Email")
                }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(20.dp))
            }


            if (tags.value != null) items(tags.value!!) { item ->
                val tag = item.first
                val size = item.second
                Box(
                    modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center
                ) {
                    TagCard(tag, size) {
                        navController.navigate(Screens.MainApp.TaskByCategory.route + "/${it.name}")
                    }
                }

            }

        }

    }

}

@Composable
private fun TagCard(tag: Tags, size: Int, onClick: (tag: Tags) -> Unit = {}) {
    Card(
        modifier = Modifier
            .sizeIn(maxWidth = 200.dp, maxHeight = 200.dp)
            .aspectRatio(1f)
            .clickable {
                onClick(tag)
            }, shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(
            containerColor = ColorUtils.stringToColor(tag.color).copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = iconByName(tag.icon),
                contentDescription = tag.name,
                tint = Color.White,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(
                        ColorUtils
                            .stringToColor(tag.color)
                            .copy(alpha = 0.5f)
                    )
                    .padding(14.dp)
                    .fillMaxSize(.3f)
            )
            Spacer(modifier = Modifier.fillMaxSize(.1f))

            Column(
                modifier = Modifier.fillMaxSize(.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tag.name,
                    fontSize = 14.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$size tasks", fontSize = 10.sp, color = DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagCardPreview() {
    TagCard(
        tag = Tags(
            "Tag1", ColorUtils.colorToString(PrimaryColor), getIconName(Icons.Filled.Home)
        ), size = 10
    )
}

@Composable
private fun ProfilePhoto(photo: Uri?) {
    Card(
        modifier = Modifier.size(64.dp),
        shape = RoundedCornerShape(50),//use 20 if you want to round corners like the one in the design
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),

        ) {
        //todo use coil
        if (photo.toString().isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar_male),
                contentDescription = "profile picture",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = photo,
                contentDescription = "profile picture",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}