package com.daveloper.rickandmortyapp.feature_character.presentation.character_details.components

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.daveloper.rickandmortyapp.core.ui.components.handlers.BackPressHandler
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.presentation.character_details.CharacterDetailsViewModel

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
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back"
            )
        }
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
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ),
            text = state.character.name,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}