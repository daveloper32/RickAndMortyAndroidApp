package com.daveloper.rickandmortyapp.core.ui.components.custom.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextWithHeader(
    modifier: Modifier = Modifier,
    header: String,
    value: String,
    fontSize: TextUnit = 16.sp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$header: ",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize
        )
        Text(
            text = TextNotEmptyValidation(value = value),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = fontSize
        )
    }
}