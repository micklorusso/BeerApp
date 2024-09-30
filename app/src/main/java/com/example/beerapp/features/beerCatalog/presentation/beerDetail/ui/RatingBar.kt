package com.example.beerapp.features.beerCatalog.presentation.beerDetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beerapp.ui.theme.Gold
import java.util.Locale

@Composable
fun RatingBar(rating: Double, reviews: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 24.sp)){
                    append("Rating: ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Gold, fontSize = 24.sp)){
                    append("%.2f".format(rating))
                }
            },
            modifier = Modifier.padding(bottom = 4.dp)
        )


        Text(
            text = "$reviews reviews",
            color = Color.Black,
            fontSize = 24.sp
        )
    }
}