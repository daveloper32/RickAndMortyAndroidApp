package com.daveloper.rickandmortyapp.feature_character.presentation.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.custom.AnimatedVisibilityFloatingActionButton
import com.daveloper.rickandmortyapp.core.ui.components.custom.Chip
import com.daveloper.rickandmortyapp.core.ui.components.custom.FilterSelector
import com.daveloper.rickandmortyapp.core.ui.components.custom.NotFoundDataCmp
import com.daveloper.rickandmortyapp.core.ui.components.handlers.AutoFinishBackPressHandler
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import com.daveloper.rickandmortyapp.core.ui.vectors.AppIcon
import com.daveloper.rickandmortyapp.core.ui.vectors.AppIcon.arrowUpward
import com.daveloper.rickandmortyapp.feature_character.domain.enums.CharacterFilterType
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersEvent
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharactersViewModel = hiltViewModel(),
    onUpdateScrollPosition: ((newPosition: Int) -> Unit)? = null
) {
    AutoFinishBackPressHandler()
    val state = viewModel.state.value
    val searchText = viewModel.searchText.value
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    onUpdateScrollPosition?.invoke(scrollState.firstVisibleItemIndex)
    viewModel.onEvent(
        CharactersEvent.ScrollPosition(scrollState.firstVisibleItemIndex)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp
                        )
                        .padding(
                            top = 16.dp
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .padding(
                                end = 16.dp
                            )
                            .weight(1f)
                        ,
                        value = searchText.text,
                        onValueChange = {
                            viewModel.onEvent(CharactersEvent.Search(it))
                        },
                        label = {
                            Text(
                                searchText.hint
                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search bar"
                            )
                        },
                        trailingIcon = {
                            if (searchText.text.isNotEmpty()) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        viewModel.onEvent(CharactersEvent.ClearSearchBar)
                                    },
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Search bar dismiss"
                                )
                            }
                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .size(24.dp),
                        onClick = {
                            viewModel.onEvent(
                                CharactersEvent.ActivateFilter
                            )
                        }
                    ) {
                        Icon(
                            imageVector = AppIcon.filterAlt(),
                            contentDescription = "Filter"
                        )
                    }
                }
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }
            ) {
                Column {
                    LazyColumn {
                        item {
                            AnimatedVisibility(
                                visible = state.isFilterResumeVisible,
                                enter = fadeIn() + slideInVertically(),
                                exit = fadeOut() + slideOutVertically()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp
                                        )
                                        .padding(
                                            top = 4.dp
                                        ),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Chip(
                                        name = state.selectedLifeStatus,
                                        subTitle = stringResource(id = R.string.lab_life_status),
                                        onChipClicked = {
                                            viewModel.onEvent(
                                                CharactersEvent.ActivateFilter
                                            )
                                        }
                                    )
                                    Chip(
                                        name = state.selectedSpecies,
                                        subTitle = stringResource(id = R.string.lab_species),
                                        onChipClicked = {
                                            viewModel.onEvent(
                                                CharactersEvent.ActivateFilter
                                            )
                                        }
                                    )
                                    Chip(
                                        name = state.selectedGender,
                                        subTitle = stringResource(id = R.string.lab_gender),
                                        onChipClicked = {
                                            viewModel.onEvent(
                                                CharactersEvent.ActivateFilter
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        item {
                            AnimatedVisibility(
                                visible = state.isFilterSelectorVisible,
                                enter = fadeIn() + slideInVertically(),
                                exit = fadeOut() + slideOutVertically()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 16.dp
                                        )
                                        .padding(
                                            top = 8.dp
                                        ),
                                ) {
                                    FilterSelector(
                                        subTitle = stringResource(id = R.string.lab_life_status),
                                        data = state.lifeStatus,
                                    ) { selectedValue ->
                                        viewModel.onEvent(
                                            CharactersEvent.Filter(
                                                characterFilterType = CharacterFilterType.LIFE_STATUS,
                                                value = selectedValue
                                            )
                                        )
                                    }
                                    FilterSelector(
                                        subTitle = stringResource(id = R.string.lab_species),
                                        data = state.species,
                                    ) { selectedValue ->
                                        viewModel.onEvent(
                                            CharactersEvent.Filter(
                                                characterFilterType = CharacterFilterType.SPECIES,
                                                value = selectedValue
                                            )
                                        )
                                    }
                                    FilterSelector(
                                        subTitle = stringResource(id = R.string.lab_gender),
                                        data = state.genders,
                                    ) { selectedValue ->
                                        viewModel.onEvent(
                                            CharactersEvent.Filter(
                                                characterFilterType = CharacterFilterType.GENDER,
                                                value = selectedValue
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (state.isLoading) {
                        CharactersLoading()
                    }
                    if (!state.isNotFoundDataVisible) {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(
                                //horizontal = 4.dp
                            ),
                            state = scrollState,
                        ) {
                            items(
                                count = state.characters.size,
                                key = {
                                    state.characters[it].id
                                },
                            ) {
                                CharacterItem(
                                    character = state.characters[it]
                                ) {
                                    navController.navigate(
                                        Screen.CharacterDetailsScreen.createRoute(
                                            characterId = it.id
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                NotFoundDataCmp(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                )
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
            imageVector = arrowUpward(),
            onClick = {
                scope.launch {
                    scrollState.scrollToItem(0)
                }
            }
        )
    }
}

@Composable
fun CharactersLoading(
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            //horizontal = 4.dp
        ),
        //state = state,
    ) {
        items(
            count = 10,
        ) {
            CharacterShimmerItem()
        }
    }
}