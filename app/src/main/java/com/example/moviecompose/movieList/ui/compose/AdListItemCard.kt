package com.example.moviecompose.movieList.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.movieList.domain.model.Ad
import com.example.moviecompose.movieList.ui.modelUI.AdUI
import com.example.moviecompose.ui.theme.MovieComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdListItemCard(
    ad: AdUI = AdUI(
        "1Fit",
        "Абонемент на все виды спорта",
        "https://resources.cdn-kaspi.kz/shop/medias/sys_master/images/images/h4b/hf7/47592727773214/1fit-bezlimit-3-mesaca-101420202-1-Container.png"
    )
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = {},
        modifier = Modifier
            .padding(5.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(5.dp)
    ) {
        AdListItemContent(
            ad = ad
        )
    }
}

@Composable
fun AdListItemContent(
    ad: AdUI
){
    AsyncImage(
        model = ad.image,
        placeholder = painterResource(R.drawable.loading_image),
        contentDescription = null,
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth(),
    )
    Text(
        text = ad.title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 5.dp)
    )
    Text(
        text = ad.description,
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 5.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AdListItemPreview() {
    MovieComposeTheme {
        AdListItemCard()
    }
}