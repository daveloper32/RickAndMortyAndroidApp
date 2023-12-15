package com.daveloper.rickandmortyapp.feature_character.presentation.character_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.custom.text.TextWithHeader
import com.daveloper.rickandmortyapp.core.ui.components.handlers.BackPressHandler
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import com.daveloper.rickandmortyapp.feature_character.presentation.character_details.CharacterDetailsViewModel
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodeHorizontalItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
) {
    BackPressHandler {
        navController.popBackStack()
    }
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterDetailsAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 6.dp
                ),
        ) {
            navController.popBackStack()
        }
        Spacer(modifier = Modifier.size(4.dp))
        LazyColumn() {
            item {
                GlideImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(1.dp))
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop,
                    model = state.character.imageUrl,
                    contentDescription = state.character.name,
                    loading = placeholder(
                        R.drawable.ic_not_character_found
                    ),
                    failure = placeholder(
                        R.drawable.ic_not_character_found
                    ),
                )
                Spacer(modifier = Modifier.size(4.dp))
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp
                            ),
                        text = state.character.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_life_status),
                        value = state.character.lifeStatus
                    )
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_gender),
                        value = state.character.gender
                    )
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_current_location),
                        value = state.character.currentLocation
                    )
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_origin),
                        value = state.character.origin,
                    )
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_species),
                        value =  state.character.species
                    )
                    TextWithHeader(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            ),
                        header = stringResource(id = R.string.lab_type),
                        value =  state.character.type
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    if (state.episodes.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.lab_episodes),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
            items(
                count = state.episodes.size,
                key = {
                    state.episodes[it].id
                },
            ) {
                EpisodeHorizontalItem(
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp
                        ),
                    episode = state.episodes[it]
                ) {
                    navController.navigate(
                        Screen.EpisodeDetailsScreen.createRoute(
                            episodeId = it.id
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
private fun CharacterDetailsAppBar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
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
            Spacer(modifier = Modifier.size(4.dp))
            IconButton(
                onClick = {
                    onClick?.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.lab_character_details),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}