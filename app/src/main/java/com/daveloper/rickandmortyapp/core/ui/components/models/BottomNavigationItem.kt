package com.daveloper.rickandmortyapp.core.ui.components.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

/** Data model descriptor of some item internal data for a Bottom Navigation Component
 * @param label ([String] type) - Name to display on item.
 * @param icon ([ImageVector] type) - Icon to display on item.
 * @param route ([String] type) - Route/destination to navigate when user clicks on item.
 * */
data class BottomNavigationItem(
    val label : String = Constants.EMPTY_STR,
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = Constants.EMPTY_STR
)
