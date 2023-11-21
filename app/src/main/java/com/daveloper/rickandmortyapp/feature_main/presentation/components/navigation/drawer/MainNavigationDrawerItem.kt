package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationDrawerItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    label: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    NavigationDrawerItem(
        modifier = modifier
            .padding(
                horizontal = 8.dp
            )
            .padding(
                bottom = 8.dp
            ),
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
        shape = ShapeDefaults.Small,
        onClick = {
            onClick?.invoke()
        }
    )
}