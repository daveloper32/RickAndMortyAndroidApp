package com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier,
    onClick: ((Episode) -> Unit)? = null
) {
    Card(
        modifier = modifier
            .padding(
                8.dp
            ),
        onClick = {
            onClick?.invoke(episode)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            ) {
                GlideImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(1.dp))
                        .fillMaxWidth()
                        .height(218.dp),
                    contentScale = ContentScale.Crop,
                    model = R.drawable.ic_episode_frame,
                    contentDescription = "Episode frame",
                    loading = placeholder(
                        R.drawable.ic_episode_frame
                    ),
                    failure = placeholder(
                        R.drawable.ic_episode_frame
                    ),
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        shape = RectangleShape
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 4.dp
                                ),
                            text = stringResource(
                                id = R.string.lab_season_and_episode_stamp,
                                episode.seasonNumber,
                                episode.episodeNumber
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Card(
                        shape = RectangleShape
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 4.dp
                                ),
                            text = episode.name,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = episode.airDate,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
        }
    }
}