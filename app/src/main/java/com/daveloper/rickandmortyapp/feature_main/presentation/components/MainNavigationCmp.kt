package com.daveloper.rickandmortyapp.feature_main.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.components.CharactersScreen
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodesScreen
import com.daveloper.rickandmortyapp.feature_location.presentation.locations.components.LocationsScreen
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen

@Composable
fun MainNavigationCmp() {
    BottomNavigationBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.CharactersScreen.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
                composable(Screen.CharactersScreen.route) {
                    CharactersScreen()
                }
                composable(Screen.EpisodesScreen.route) {
                    EpisodesScreen()
                }
                composable(Screen.LocationsScreen.route) {
                    LocationsScreen()
                }
        }
    }
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Characters",
                icon = Icons.Filled.Person,
                route = Screen.CharactersScreen.route
            ),
            BottomNavigationItem(
                label = "Episodes",
                icon = Icons.Filled.Star,
                route = Screen.EpisodesScreen.route
            ),
            BottomNavigationItem(
                label = "Locations",
                icon = Icons.Filled.LocationOn,
                route = Screen.LocationsScreen.route
            ),
        )
    }
}