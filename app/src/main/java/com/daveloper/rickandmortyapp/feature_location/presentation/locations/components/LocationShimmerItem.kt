package com.daveloper.rickandmortyapp.feature_location.presentation.locations.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.modifier.shimmerEffect

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationShimmerItem(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(
                8.dp
            ),
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
                        .height(248.dp)
                        .shimmerEffect(),
                    contentScale = ContentScale.Crop,
                    model = R.drawable.ic_place_frame,
                    contentDescription = R.drawable.ic_place_frame.toString(),
                    loading = placeholder(
                        R.drawable.ic_place_frame
                    ),
                    failure = placeholder(
                        R.drawable.ic_place_frame
                    ),
                )
                Card(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(12.dp),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = 4.dp
                            ).shimmerEffect(true),
                        text = "                    ",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                ) {
                    Card(
                        shape = RoundedCornerShape(12)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 4.dp
                                ).shimmerEffect(true),
                            text = "               ",
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Card(
                        shape = RoundedCornerShape(12)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    horizontal = 4.dp
                                ).shimmerEffect(true),
                            text = "          ",
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}