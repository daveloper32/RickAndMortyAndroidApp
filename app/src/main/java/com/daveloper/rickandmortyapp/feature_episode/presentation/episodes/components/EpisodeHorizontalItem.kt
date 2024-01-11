package com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun EpisodeHorizontalItem(
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(1.dp))
                    .width(100.dp)
                    .height(100.dp),
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
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(
                        horizontal = 4.dp
                    )
                    .padding(
                        start = 8.dp
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.lab_season_and_episode_stamp,
                        episode.seasonNumber,
                        episode.episodeNumber
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = episode.name,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 2.dp
                        ),
                    text = episode.airDate,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
        }
    }
}