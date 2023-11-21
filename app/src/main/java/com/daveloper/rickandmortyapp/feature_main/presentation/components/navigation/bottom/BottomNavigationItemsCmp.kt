package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.models.BottomNavigationItem
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen

@Composable
fun BottomNavigationItems() : List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = stringResource(id = R.string.lab_characters),
            icon = Icons.Filled.Person,
            route = Screen.CharactersScreen.route
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.lab_episodes),
            icon = Icons.Filled.Star,
            route = Screen.EpisodesScreen.route
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.lab_locations),
            icon = Icons.Filled.LocationOn,
            route = Screen.LocationsScreen.route
        ),
    )
}