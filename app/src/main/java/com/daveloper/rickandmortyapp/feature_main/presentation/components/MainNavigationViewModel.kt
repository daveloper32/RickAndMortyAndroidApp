package com.daveloper.rickandmortyapp.feature_main.presentation.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(

): ViewModel() {
    companion object {
        private val TAG = MainNavigationViewModel::class.java.name
    }
    // Last scroll index/position on view
    private var lastScrollPosition = 0
    // View Model connexion state
    private val _state = mutableStateOf(
        MainNavigationState()
    )
    val state: State<MainNavigationState> = _state

    /** Receive all the events from the view/screen
     * @param event ([MainNavigationEvent] type)
     * */
    fun onEvent(
        event: MainNavigationEvent
    ) {
        try {
            when (event) {
                is MainNavigationEvent.ScrollPosition -> {
                    updateScrollPosition(event.newPosition)
                }
                is MainNavigationEvent.Navigation -> {
                    _state.value = _state.value.copy(
                        isBottomNavigationBarVisible = when (event.screen) {
                            Screen.CharacterDetailsScreen,
                            Screen.EpisodeDetailsScreen,
                            Screen.LocationDetailsScreen-> false
                            else -> true
                        }
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent() error -> $e")
        }
    }

    /** Updates the current scroll position an make some actions on the view state based on the update.
     * */
    private fun updateScrollPosition(
        newPosition: Int
    ) {
        try {
            if (
                newPosition == lastScrollPosition
            ) {
                return
            }
            _state.value = _state.value.copy(
                isScrollingUp = newPosition > lastScrollPosition
            )
            lastScrollPosition = newPosition
        } catch (e: Exception) {
            Log.e(TAG, "updateScrollPosition() error -> $e", )
        }
    }
}