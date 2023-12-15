package com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.ui.components.models.NavigationDrawerItem
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen

@Composable
fun NavigationDrawerItems(): List<NavigationDrawerItem> {
    return listOf(
        NavigationDrawerItem(
            label = stringResource(id = R.string.lab_home),
            icon = Icons.Filled.Home,
            route = Screen.HomeScreen.route
        ),
        NavigationDrawerItem(
            label = stringResource(id = R.string.lab_characters),
            icon = Icons.Filled.Person,
            route = Screen.CharactersScreen.route
        ),
        NavigationDrawerItem(
            label = stringResource(id = R.string.lab_episodes),
            icon = Icons.Filled.Star,
            route = Screen.EpisodesScreen.route
        ),
        NavigationDrawerItem(
            label = stringResource(id = R.string.lab_locations),
            icon = Icons.Filled.LocationOn,
            route = Screen.LocationsScreen.route
        ),
    )
}