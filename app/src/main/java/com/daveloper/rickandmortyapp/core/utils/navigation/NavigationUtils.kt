package com.daveloper.rickandmortyapp.core.utils.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object NavigationUtils {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    fun onNavigationItemClicked(
        route: String,
        navController: NavController,
        coroutineScope: CoroutineScope? = null,
        drawerState: DrawerState? = null,
    ) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        closeNavigationDrawer(coroutineScope, drawerState)
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    fun closeNavigationDrawer(
        coroutineScope: CoroutineScope? = null,
        drawerState: DrawerState? = null,
    ) {
        coroutineScope?.launch {
            drawerState?.apply {
                if (isClosed) open() else close()
            }
        }
    }
}