package com.daveloper.rickandmortyapp.core.ui.components.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

@Composable
fun AnimatedVisibilityFloatingActionButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    imageVector: ImageVector,
    contentDescription: String = Constants.EMPTY_STR,
    size: Int = 25,
    onClick: (() -> Unit)? = null
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(),
        //exit = slideOutVertically(),
    ) {
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp),
            onClick = {
                onClick?.invoke()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(size.dp),
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}