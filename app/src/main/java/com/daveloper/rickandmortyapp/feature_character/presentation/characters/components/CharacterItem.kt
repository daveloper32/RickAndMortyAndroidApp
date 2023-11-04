package com.daveloper.rickandmortyapp.feature_character.presentation.characters.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.placeholder
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(
                8.dp
            ),
        onClick = { /*TODO*/ }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    //.fillMaxWidth()
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = character.imageUrl,
                contentDescription = character.name,
                loading = placeholder(
                    R.drawable.ic_not_character_found
                ),
                failure = placeholder(
                    R.drawable.ic_not_character_found
                ),
            )
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                text = character.name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                    )
                    .padding(
                        bottom = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            end = 4.dp
                        )
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            when (character.lifeStatus.lowercase()) {
                                "alive" -> Color(0xFF78C34A)
                                "dead" -> Color(0xFFE24034)
                                else -> Color(0xFFE9B310)
                            }
                        )
                )
                Text(
                    text = "${character.lifeStatus} - ${character.species}",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
        }
    }
}