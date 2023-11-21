package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daveloper.rickandmortyapp.core.ui.components.models.BottomNavigationItem

@Composable
fun MainBottomNavigation(
    isVisible: Boolean = true,
    currentIndexItemSelected: Int,
    onItemClicked: (
        (
        index: Int,
        bottomNavigationItem: BottomNavigationItem
    ) -> Unit
    )? = null,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(),
        //exit = slideOutVertically(),
    ) {
        NavigationBar(
            modifier = Modifier
        ) {
            BottomNavigationItems().forEachIndexed { index, bottomNavigationItem ->
                MainBottomNavigationItem(
                    isSelected = index == currentIndexItemSelected,
                    label = bottomNavigationItem.label,
                    icon = bottomNavigationItem.icon,
                    onClick = {
                        onItemClicked?.invoke(index, bottomNavigationItem)
                    }
                )
            }
        }
    }
}