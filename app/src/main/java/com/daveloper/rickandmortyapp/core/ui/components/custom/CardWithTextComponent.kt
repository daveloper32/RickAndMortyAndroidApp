package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithTextComponent(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(
                8.dp
            ),
        onClick = { onClick?.invoke() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(
                            end = 8.dp
                        ),
                    imageVector = icon,
                    contentDescription = label
                )
            }
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                text = label,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}