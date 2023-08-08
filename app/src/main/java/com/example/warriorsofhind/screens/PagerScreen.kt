package com.example.warriorsofhind.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.viewmodel.WarriorsNameViewModel
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import kotlinx.coroutines.launch

@Composable
fun PagerScreen(modifier: Modifier = Modifier, onClick: (arg: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
    val responseState = viewModel.warriorsNameListState.observeAsState()


    when (responseState.value) {
        is NetworkStatusWrapper.Success<*> -> {
            val kingList = responseState.value?.data
            if (kingList != null) {
                PagerUI(kingList, onClick = onClick)
            }
        }

        is NetworkStatusWrapper.Loading<*> -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.loading)
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.server_error)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerUI(kingList: List<King>, onClick: (arg: String) -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = .25f)

    var pageCount by remember { mutableStateOf(kingList.size) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = pageCount,
                modifier = Modifier.fillMaxSize()
            ) { currentPage ->
                Box(modifier = Modifier.fillMaxSize().clickable {
                    onClick(kingList[currentPage].name)
                }) {
                    PagerUIImage(imgUrl = kingList[currentPage].img)
                    Text(
                        text = kingList[currentPage].name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(start = 8.dp, bottom = 8.dp)
                            .fillMaxWidth()

                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        DotsIndicator(
            dotCount = pageCount,
            type = ShiftIndicatorType(dotsGraphic = DotGraphic(color = MaterialTheme.colorScheme.primary)),
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val coroutineScope = rememberCoroutineScope()

            val showPre by remember {
                derivedStateOf {
                    pagerState.currentPage > 0
                }
            }

            val showNext by remember {
                derivedStateOf {
                    pagerState.currentPage < kingList.size - 1
                }
            }
            IconButton(
                enabled = showPre,
                onClick = {
                    val prePage = pagerState.currentPage - 1
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(prePage)
                    }
                },
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            IconButton(
                enabled = showNext,
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(nextPage)
                    }
                },
            ) {
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null,
                )
            }
        }
    }
}


@Composable
fun PagerUIImage(imgUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .error(R.drawable.error_img)
            .build(),
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
        contentDescription = "This is an example image"
    )
}