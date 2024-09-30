package com.example.beerapp.features.beerCatalog.presentation.beerDetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.beerapp.R
import com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel.BeerDetailEvent
import com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel.BeerDetailState
import com.example.beerapp.features.beerCatalog.presentation.beerDetail.viewModel.BeerDetailViewModel
import com.example.beerapp.features.beerCatalog.presentation.beerList.ui.BeerItem
import com.example.beerapp.features.beerCatalog.presentation.beerList.viewModel.BeerListEvent
import com.example.beerapp.ui.theme.Gold
import com.example.beerapp.util.LoadingIndicator
import com.example.beerapp.util.UiSingleTimeEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetailScreen(
    id: Int,
    navigator: NavHostController,
    viewModel: BeerDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(id) {
        viewModel.getBeerDetail(id)
    }

    val state = viewModel.state.collectAsState(initial = BeerDetailState.Empty)

    when (state.value) {
        is BeerDetailState.Loading -> {
            LoadingIndicator()
        }
        is BeerDetailState.Failure -> {
            val errorText = (state.value as BeerDetailState.Failure).errorText
            Text("Error: $errorText")
        }
        is BeerDetailState.Success -> {
            LaunchedEffect(key1 = true) {
                viewModel.uiSingleTimeEvent.collect { event ->
                    when(event) {
                        is UiSingleTimeEvent.PopBackStack -> navigator.popBackStack()
                        else -> Unit
                    }
                }
            }

            val beerDetail = (state.value as BeerDetailState.Success).beerDetail
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(beerDetail.name) },
                        navigationIcon = {
                            IconButton(onClick = { viewModel.onEvent(BeerDetailEvent.OnBackClicked) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color.White)
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(beerDetail.image),
                        contentDescription = "${beerDetail.name} image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Fit
                    )


                    Text(
                        text = beerDetail.name,
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                    Text(
                        text = beerDetail.price,
                        color = Gold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    RatingBar(
                        rating = beerDetail.rating.average,
                        reviews = beerDetail.rating.reviews
                    )
                }
            }
        }
        else -> Unit
    }


}