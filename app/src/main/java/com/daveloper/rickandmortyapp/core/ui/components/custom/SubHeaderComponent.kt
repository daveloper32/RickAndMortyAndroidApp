package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SubHeaderComponent(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    end = 8.dp
                ),
            imageVector = icon,
            contentDescription = label
        )
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold
        )
    }
}