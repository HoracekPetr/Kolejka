package com.example.kolejka.view.ui.screens.news_detail_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.models.News
import com.example.kolejka.view.theme.PaddingMedium
import com.example.kolejka.view.theme.Space12
import com.example.kolejka.view.theme.Space8
import com.example.kolejka.view.theme.roboto_mono

@Composable
fun NewsDetailScreen(
    newsId: String? = null,
    viewModel: NewsDetailScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val news = viewModel.newsDetail.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingMedium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = news?.title ?: "",
            style = if (news?.title?.length ?: 0 < 15) MaterialTheme.typography.body1 else TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            ),
            color = MaterialTheme.colors.primary,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )

        Spacer(modifier = Modifier.size(Space8))

        Text(
            modifier = Modifier.align(Alignment.End),
            text = news?.formattedTime ?: "",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.size(Space12))
        Divider(color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.size(Space12))

        Text(
            text = news?.description ?: "",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
    }
}