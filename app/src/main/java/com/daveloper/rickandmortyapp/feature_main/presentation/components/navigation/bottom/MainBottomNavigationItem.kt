package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.bottom

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RowScope.MainBottomNavigationItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    label: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    NavigationBarItem(
        modifier = modifier,
        selected = isSelected,
        label = {
            Text(text = label)
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        },
        onClick = {
            onClick?.invoke()
        }
    )
}