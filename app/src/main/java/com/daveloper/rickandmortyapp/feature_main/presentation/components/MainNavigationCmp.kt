package com.daveloper.rickandmortyapp.feature_main.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daveloper.rickandmortyapp.core.utils.navigation.NavigationUtils.closeNavigationDrawer
import com.daveloper.rickandmortyapp.core.utils.navigation.NavigationUtils.onNavigationItemClicked
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.components.CharactersScreen
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodesScreen
import com.daveloper.rickandmortyapp.feature_location.presentation.locations.components.LocationsScreen
import com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.bottom.MainBottomNavigation
import com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.drawer.MainNavigationDrawer
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationCmp(
    viewModel: MainNavigationViewModel = hiltViewModel(),
) {
    var navigationSelectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    val state = viewModel.state.value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    MainNavigationDrawer(
        drawerState = drawerState,
        currentIndexItemSelected = navigationSelectedItem,
        onItemClicked = { index, navigationDrawerItem ->
            navigationSelectedItem = index
            onNavigationItemClicked(
                route = navigationDrawerItem.route,
                navController = navController,
                coroutineScope = scope,
                drawerState = drawerState
            )
        },
        onClose = {
            closeNavigationDrawer(scope, drawerState)
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            bottomBar = {
                MainBottomNavigation(
                    isVisible = !state.isScrollingUp,
                    currentIndexItemSelected = navigationSelectedItem,
                    onItemClicked = { index, bottomNavigationItem ->
                        navigationSelectedItem = index
                        onNavigationItemClicked(
                            route = bottomNavigationItem.route,
                            navController = navController
                        )
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.CharactersScreen.route,
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
            ) {
                composable(Screen.CharactersScreen.route) {
                    CharactersScreen(
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
                composable(Screen.EpisodesScreen.route) {
                    EpisodesScreen(
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
                composable(Screen.LocationsScreen.route) {
                    LocationsScreen(
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
            }
        }
    }
}