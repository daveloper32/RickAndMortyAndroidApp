package com.daveloper.rickandmortyapp.feature_home.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.custom.AnimatedVisibilityFloatingActionButton
import com.daveloper.rickandmortyapp.core.ui.components.custom.CardWithTextComponent
import com.daveloper.rickandmortyapp.core.ui.components.custom.SubHeaderComponent
import com.daveloper.rickandmortyapp.core.ui.vectors.AppIcon
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.components.CharacterItem
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodeItem
import com.daveloper.rickandmortyapp.feature_home.domain.enums.AppDataType
import com.daveloper.rickandmortyapp.feature_home.presentation.HomeEvent
import com.daveloper.rickandmortyapp.feature_home.presentation.HomeUIState
import com.daveloper.rickandmortyapp.feature_home.presentation.HomeViewModel
import com.daveloper.rickandmortyapp.feature_location.presentation.locations.components.LocationItem
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    //navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    onUpdateScrollPosition: ((newPosition: Int) -> Unit)? = null,
    onNavigate: ((Screen) -> Unit)? = null
) {
    val state = viewModel.state.value
    val uiState = viewModel.uiState.value
    when (uiState) {
        is HomeUIState.NavigateTo -> {
            uiState.screen?.let {
                onNavigate?.invoke(
                    it
                )
                viewModel.onEvent(HomeEvent.NavigationCompleted)
            }
        }
    }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    onUpdateScrollPosition?.invoke(scrollState.firstVisibleItemIndex)
    viewModel.onEvent(
        HomeEvent.ScrollPosition(scrollState.firstVisibleItemIndex)
    )

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = !state.isScrollingUp,
                enter = slideInVertically(),
                //exit = slideOutVertically(),
            ) {
               HomeAppBar(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(
                           vertical = 12.dp,
                           horizontal = 12.dp
                       )
               )
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvent(HomeEvent.Refresh) }
            ) {
                LazyColumn(
                    state = scrollState
                ) {
                    item {
                        SubHeaderComponent(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                                .padding(
                                    top = 16.dp,
                                    bottom = 8.dp
                                ),
                            label = stringResource(id = R.string.lab_characters),
                            icon = Icons.Filled.Person
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            state = rememberLazyListState()
                        ) {
                            items(
                                count = state.characters.size,
                                key = {
                                    state.characters[it].id
                                },
                            ) {
                                CharacterItem(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(268.dp),
                                    character = state.characters[it],
                                )
                            }
                            item {
                                CardWithTextComponent(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(268.dp),
                                    label = stringResource(id = R.string.lab_see_more),
                                    icon = Icons.Rounded.Add,
                                ) {
                                    viewModel.onEvent(HomeEvent.LoadMoreData(AppDataType.CHARACTER))
                                }
                            }
                        }
                    }
                    item {
                        SubHeaderComponent(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                                .padding(
                                    top = 12.dp,
                                    bottom = 8.dp
                                ),
                            label = stringResource(id = R.string.lab_episodes),
                            icon = Icons.Filled.Star
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            state = rememberLazyListState()
                        ) {
                            items(
                                count = state.episodes.size,
                                key = {
                                    state.episodes[it].id
                                },
                            ) {
                                EpisodeItem(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(268.dp),
                                    episode = state.episodes[it],
                                )
                            }
                            item {
                                CardWithTextComponent(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(268.dp),
                                    label = stringResource(id = R.string.lab_see_more),
                                    icon = Icons.Filled.Add,
                                ) {
                                    viewModel.onEvent(HomeEvent.LoadMoreData(AppDataType.EPISODE))
                                }
                            }
                        }
                    }
                    item {
                        SubHeaderComponent(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                                .padding(
                                    top = 12.dp,
                                    bottom = 8.dp
                                ),
                            label = stringResource(id = R.string.lab_locations),
                            icon = Icons.Filled.LocationOn
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = 16.dp
                                ),
                            state = rememberLazyListState()
                        ) {
                            items(
                                count = state.locations.size,
                                key = {
                                    state.locations[it].id
                                },
                            ) {
                                LocationItem(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(264.dp),
                                    location = state.locations[it],
                                )
                            }
                            item {
                                CardWithTextComponent(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(264.dp),
                                    label = stringResource(id = R.string.lab_see_more),
                                    icon = Icons.Filled.Add,
                                ) {
                                    viewModel.onEvent(HomeEvent.LoadMoreData(AppDataType.LOCATION))
                                }
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibilityFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            isVisible = state.isScrollUpButtonVisible,
            imageVector = AppIcon.arrowUpward(),
            onClick = {
                scope.launch {
                    scrollState.scrollToItem(0)
                }
            }
        )
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    Card(
        shape = AbsoluteCutCornerShape(
            bottomLeftPercent = 6,
            bottomRightPercent = 6,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_app_logo
                ),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .clip(
                        CircleShape // Circle the image corners
                    )
                    .size(36.dp)

            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
    }
}