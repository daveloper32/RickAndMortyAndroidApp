package com.daveloper.rickandmortyapp.feature_location.presentation.location_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharactersByIdUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_location.domain.GetLocationByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resourceProvider: ResourceProvider,
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getCharactersByIdUseCase: GetCharactersByIdUseCase,
): ViewModel() {
    companion object {
        private val TAG = LocationDetailsViewModel::class.java.name
    }
    // View Model connexion state
    private val _state = mutableStateOf(
        LocationDetailsState()
    )
    val state: State<LocationDetailsState> = _state

    // Job to control the characters flow
    private var getCharactersJob: Job? = null

    init {
        try {
            loadInitData()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Loads the [Location] data based on the [Screen.LocationDetailsScreen.LOCATION_ID_PARAM]
     * input value from the [SavedStateHandle].
     * */
    private fun loadInitData() {
        try {
            savedStateHandle
                .get<Int>(
                    Screen.LocationDetailsScreen.LOCATION_ID_PARAM
                )?.let { locationId ->
                    if (locationId != Constants.INVALID_INT) {
                        viewModelScope.launch {
                            getLocationInfo(locationId)
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "loadInitData() error -> $e", )
        }
    }

    /** Try to search get the [Location] data based on the input id and setup the data on screen
     * via [LocationDetailsState].
     *
     * @param id ([Int] type)
     * */
    private suspend fun getLocationInfo(
        id: Int
    ) {
        try {
            val result = getLocationByIdUseCase.invoke(
                id = id
            )
            when (result) {
                is UseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        location = result.data!!
                    )
                    getCharactersFromLocation(result.data.residentIds)
                }
                is UseCaseResult.Message -> {
                    // TODO set up message error handling
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodeInfo() error -> $e", )
        }
    }

    /** Get and search all the [Character] where the [Location] appears found to the view, handling
     * the [GetCharactersByIdUseCase] response ([Flow]<[List]<[Character]>>) to a local [Job] that
     * could be cancelled at any time.
     *
     * @param ids ([List]<[Int]> type)
     * */
    private fun getCharactersFromLocation(
        ids: List<Int> = emptyList()
    ) {
        try {
            getCharactersJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharactersJob = getCharactersByIdUseCase
                .invoke(
                    ids = ids
                )
                .onEach { characters ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        characters = characters,
                        isNotFoundDataVisible = characters.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodes() error -> $e")
        }
    }

    /** Close / stop / re-start all the [LocationDetailsViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getCharactersJob?.cancel()
        } catch (e: Exception) {
            Log.e(TAG, "stopViewModelControls() error -> $e")
        }
    }

    override fun onCleared() {
        try {
            stopViewModelControls()
            super.onCleared()
        } catch (e: Exception) {
            Log.e(TAG, "onCleared() error -> $e")
            super.onCleared()
        }
    }
}