package com.example.taskscompose.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.R
import com.example.taskscompose.ui.theme.PrimaryColor


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val padding = 16.dp
    Column {
        Header(modifier = Modifier.padding(padding))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "My Tasks",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = padding)
        )
        Column(
            modifier = Modifier
                .padding(start = padding, end = padding)
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskCard(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                TaskCard(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskCard(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                TaskCard(modifier = Modifier.weight(1f))
            }
        }
    }

}


@Composable
private fun TaskCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Box() {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "check circle",
                        contentScale = ContentScale.Inside
                    )
                    Text(
                        "Title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = PrimaryColor
                    )
                    Text(
                        "n Task",
                        color = PrimaryColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "check circle",
                        tint = Color.Gray
                    )

                }
            }
        }

    }
}

@Composable
private fun Header(modifier: Modifier) {
    Row(
        modifier = modifier.height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                "Hi, Steven", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryColor
            )
            Text(
                "Let's make this day productive",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )

        Card(
            modifier = Modifier.size(64.dp), elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ), colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop,


                    )
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen()
}