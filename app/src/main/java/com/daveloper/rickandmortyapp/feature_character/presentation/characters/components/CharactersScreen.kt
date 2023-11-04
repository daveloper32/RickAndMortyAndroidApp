package com.daveloper.rickandmortyapp.feature_character.presentation.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.ui.components.Chip
import com.daveloper.rickandmortyapp.core.ui.vectors.AppIcon
import com.daveloper.rickandmortyapp.feature_character.domain.enums.CharacterFilterType
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersEvent
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    //navController: NavController,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val searchText = viewModel.searchText.value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    subTitle = stringResource(id = R.string.lab_life_status)
                )
                Chip(
                    name = state.selectedSpecies,
                    subTitle = stringResource(id = R.string.lab_species)
                )
                Chip(
                    name = state.selectedGender,
                    subTitle = stringResource(id = R.string.lab_gender)
                )
            }
        }
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
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }
        ) {
            if (!state.isNotFoundDataVisible) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        //horizontal = 4.dp
                    )
                ) {
                    items(state.characters) {
                        CharacterItem(
                            character = it
                        )
                    }
                }
            } else {
                NotFoundDataCmp(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FilterSelector(
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    data: List<ItemDataFilter> = listOf(),
    onSomeFilterChipSelected: ((itemSelected: String) -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        if (!subTitle.isNullOrEmpty()) {
            Text(
                text = subTitle,
                fontSize = 10.sp
            )
        }
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start
        ) {
            items(data) {
                Chip(
                    name = it.label,
                    isChecked = it.isSelected
                ) { chipValue ->
                    onSomeFilterChipSelected?.invoke(chipValue)
                }
            }
        }
    }
}

@Composable
fun NotFoundDataCmp(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .padding(
                    bottom = 8.dp
                ).size(120.dp),
            painter = painterResource(id = R.drawable.ic_not_found),
            contentDescription = stringResource(id = R.string.lab_not_found_data_error),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = stringResource(id = R.string.lab_not_found_data_error),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
        Text(
            text = stringResource(id = R.string.lab_please_try_another_search),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}