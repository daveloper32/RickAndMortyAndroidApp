package com.daveloper.rickandmortyapp.feature_main.presentation.components

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.daveloper.rickandmortyapp.core.ui.components.handlers.BackPressHandler
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import com.daveloper.rickandmortyapp.core.utils.navigation.NavigationUtils.closeNavigationDrawer
import com.daveloper.rickandmortyapp.core.utils.navigation.NavigationUtils.onNavigationItemClicked
import com.daveloper.rickandmortyapp.core.utils.navigation.NavigationUtils.openNavigationDrawer
import com.daveloper.rickandmortyapp.feature_character.presentation.character_details.components.CharacterDetailsScreen
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.components.CharactersScreen
import com.daveloper.rickandmortyapp.feature_episode.presentation.episode_details.components.EpisodeDetailsScreen
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodesScreen
import com.daveloper.rickandmortyapp.feature_home.presentation.home.HomeScreen
import com.daveloper.rickandmortyapp.feature_location.presentation.location_details.components.LocationDetailsScreen
import com.daveloper.rickandmortyapp.feature_location.presentation.locations.components.LocationsScreen
import com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.bottom.MainBottomNavigation
import com.daveloper.rickandmortyapp.feature_main.presentation.components.navigation.drawer.MainNavigationDrawer

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationCmp(
    viewModel: MainNavigationViewModel = hiltViewModel(),
) {
    var navigationSelectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val state = viewModel.state.value

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    BackPressHandler {
        context.findActivity()?.finish()
    }

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
                AnimatedVisibility(
                    visible = state.isBottomNavigationBarVisible,
                    enter = slideInVertically(),
                    //exit = slideOutVertically(),
                ) {
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
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
            ) {
                composable(
                    route = Screen.HomeScreen.route
                ) {
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.HomeScreen)
                    )
                    HomeScreen(
                        navController = navController,
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        },
                        onNavigate = { screen ->
                            navigationSelectedItem = screen.index
                            onNavigationItemClicked(
                                route = screen.route,
                                navController = navController
                            )
                        },
                        onToolbarButtonClick = {
                            openNavigationDrawer(
                                scope, drawerState
                            )
                        }
                    )
                }
                composable(
                    route = Screen.CharactersScreen.route
                ) {
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.CharactersScreen)
                    )
                    CharactersScreen(
                        navController = navController,
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
                composable(
                    route = Screen.EpisodesScreen.route
                ) {
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.EpisodesScreen)
                    )
                    EpisodesScreen(
                        navController = navController,
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
                composable(
                    route = Screen.LocationsScreen.route
                ) {
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.LocationsScreen)
                    )
                    LocationsScreen(
                        navController = navController,
                        onUpdateScrollPosition = { newPosition ->
                            viewModel.onEvent(
                                MainNavigationEvent.ScrollPosition(newPosition)
                            )
                        }
                    )
                }
                composable(
                    route = Screen.CharacterDetailsScreen.getRouteWithParams(),
                    arguments = listOf(
                        navArgument(Screen.CharacterDetailsScreen.CHARACTER_ID_PARAM) {
                            type = NavType.IntType
                            defaultValue = Screen.CharacterDetailsScreen.DEFAULT_CHARACTER_ID_PARAM_VALUE
                        }
                    )
                ) { navBackStackEntry : NavBackStackEntry ->
                    val characterId: Int? = navBackStackEntry
                        .arguments
                        ?.getInt(
                            Screen.CharacterDetailsScreen.CHARACTER_ID_PARAM
                        )
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.CharacterDetailsScreen)
                    )
                    CharacterDetailsScreen(
                        navController = navController,
                        characterId = characterId ?: Screen.CharacterDetailsScreen.DEFAULT_CHARACTER_ID_PARAM_VALUE
                    )
                }
                composable(
                    route = Screen.EpisodeDetailsScreen.getRouteWithParams(),
                    arguments = listOf(
                        navArgument(Screen.EpisodeDetailsScreen.EPISODE_ID_PARAM) {
                            type = NavType.IntType
                            defaultValue = Screen.EpisodeDetailsScreen.DEFAULT_EPISODE_ID_PARAM_VALUE
                        }
                    )
                ) { navBackStackEntry : NavBackStackEntry ->
                    val episodeId: Int? = navBackStackEntry
                        .arguments
                        ?.getInt(
                            Screen.EpisodeDetailsScreen.EPISODE_ID_PARAM
                        )
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.EpisodeDetailsScreen)
                    )
                    EpisodeDetailsScreen(
                        navController = navController,
                        episodeId = episodeId ?: Screen.EpisodeDetailsScreen.DEFAULT_EPISODE_ID_PARAM_VALUE
                    )
                }
                composable(
                    route = Screen.LocationDetailsScreen.getRouteWithParams(),
                    arguments = listOf(
                        navArgument(Screen.LocationDetailsScreen.LOCATION_ID_PARAM) {
                            type = NavType.IntType
                            defaultValue = Screen.LocationDetailsScreen.DEFAULT_LOCATION_ID_PARAM_VALUE
                        }
                    )
                ) { navBackStackEntry : NavBackStackEntry ->
                    val locationId: Int? = navBackStackEntry
                        .arguments
                        ?.getInt(
                            Screen.LocationDetailsScreen.LOCATION_ID_PARAM
                        )
                    viewModel.onEvent(
                        MainNavigationEvent.Navigation(Screen.LocationDetailsScreen)
                    )
                    LocationDetailsScreen(
                        navController = navController,
                        locationId = locationId ?: Screen.LocationDetailsScreen.DEFAULT_LOCATION_ID_PARAM_VALUE
                    )
                }
            }
        }
    }
}