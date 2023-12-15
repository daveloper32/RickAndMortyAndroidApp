package com.daveloper.rickandmortyapp.feature_home.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.ui.navigation.Screen
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharactersUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.UpdateCharactersUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodesUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.UpdateEpisodesUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_home.domain.enums.AppDataType
import com.daveloper.rickandmortyapp.feature_location.domain.GetLocationsUseCase
import com.daveloper.rickandmortyapp.feature_location.domain.UpdateLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val updateCharactersUseCase: UpdateCharactersUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val updateEpisodesUseCase: UpdateEpisodesUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateLocationsUseCase: UpdateLocationsUseCase,
): ViewModel() {
    companion object {
        private val TAG = HomeViewModel::class.java.name
    }
    // Last scroll index/position on view
    private var lastScrollPosition = 0
    // View Model connexion state
    private val _state = mutableStateOf(
        HomeState()
    )
    val state: State<HomeState> = _state
    // View Model connexion ui state
    private val _uiState = mutableStateOf<HomeUIState>(
        HomeUIState.NavigateTo()
    )
    val uiState: State<HomeUIState> get() = _uiState

    // Job to control the characters flow
    private var getCharactersJob: Job? = null
    // Job to control the episodes flow
    private var getEpisodesJob: Job? = null
    // Job to control the locations flow
    private var getLocationsJob: Job? = null

    init {
        try {
            getInitData()
            getCharacters()
            getEpisodes()
            getLocations()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Receive all the events from the view/screen
     * @param event ([HomeEvent] type)
     * */
    fun onEvent(
        event: HomeEvent
    ) {
        try {
            when (event) {
                is HomeEvent.LoadMoreData -> {
                    loadMoreData(event.dataType)
                }
                HomeEvent.Refresh -> {
                    updateAllData()
                }
                is HomeEvent.ScrollPosition -> {
                    updateScrollPosition(event.newPosition)
                }
                is HomeEvent.NavigationCompleted -> {
                    _uiState.value = HomeUIState.NavigateTo(null)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent() error -> $e")
        }
    }

    /** Get and send all the [Character] found to the view, handling the [GetCharactersUseCase]
     * response ([Flow]<[List]<[Character]>>) to a local [Job] that could be cancelled at any time.
     * */
    private fun getCharacters(
    ) {
        try {
            Log.i(TAG, "getCharacters() called")
            _state.value = state.value.copy(
                isNotFoundCharacterDataVisible = true
            )
            getCharactersJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharactersJob = getCharactersUseCase
                .invoke(
                    quantity = 10
                )
                .onEach { characters ->
                    Log.i(TAG, "getCharacters() answer")
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        characters = characters,
                        isNotFoundCharacterDataVisible = characters.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getCharacters() error -> $e")
        }
    }

    /** Get and send all the [Episode] found to the view, handling the [GetEpisodesUseCase]
     * response ([Flow]<[List]<[Episode]>>) to a local [Job] that could be cancelled at any time.
     * */
    private fun getEpisodes(
    ) {
        try {
            getEpisodesJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getEpisodesJob = getEpisodesUseCase
                .invoke(
                    quantity = 10
                )
                .onEach { episodes ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        episodes = episodes,
                        isNotFoundEpisodeDataVisible = episodes.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodes() error -> $e")
        }
    }

    /** Get and send all the [Location] found to the view, handling the [GetLocationsUseCase]
     * response ([Flow]<[List]<[Location]>>) to a local [Job] that could be cancelled at any time.
     * */
    private fun getLocations(
    ) {
        try {
            _state.value = state.value.copy(
                isNotFoundLocationDataVisible = true
            )
            getLocationsJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getLocationsJob = getLocationsUseCase
                .invoke(
                    quantity = 10
                )
                .onEach { locations ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        locations = locations,
                        isNotFoundLocationDataVisible = locations.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getLocations() error -> $e")
        }
    }

    /** Updates and get all the [Character] data, handling the [UpdateCharactersUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * @return ([Boolean] type). Tells if the process was successful or not
     * */
    private suspend fun updateCharacters(
        refreshData: Boolean = true
    ): Boolean {
        return try {
            val result = updateCharactersUseCase.invoke(
                forceDataRefresh = refreshData
            )
            when (result) {
                is UseCaseResult.Success -> {
                    true
                }
                is UseCaseResult.Message -> {
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateCharacters() error -> $e")
            false
        }
    }

    /** Updates and get all the [Episode] data, handling the [UpdateEpisodesUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * @return ([Boolean] type). Tells if the process was successful or not
     * */
    private suspend fun updateEpisodes(
        refreshData: Boolean = true
    ): Boolean {
        return try {
            val result = updateEpisodesUseCase.invoke(
                forceDataRefresh = refreshData
            )
            when (result) {
                is UseCaseResult.Success -> {
                    true
                }
                is UseCaseResult.Message -> {
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateEpisodes() error -> $e")
            false
        }
    }

    /** Updates and get all the [Location] data, handling the [UpdateLocationsUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * */
    private suspend fun updateLocations(
        refreshData: Boolean = true
    ): Boolean {
        return try {
            val result = updateLocationsUseCase.invoke(
                forceDataRefresh = refreshData
            )
            when (result) {
                is UseCaseResult.Success -> {
                    true
                }
                is UseCaseResult.Message -> {
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateLocations() error -> $e")
            false
        }
    }

    /** Gets the initial [Character] data, [Episode] data & [Location] data, if for some reason
     * it doesn't have any data on local.
     * */
    private fun getInitData() {
        try {
            viewModelScope.launch {
                updateCharacters(false)
            }
            viewModelScope.launch {
                updateEpisodes(false)
            }
            viewModelScope.launch {
                updateLocations(false)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getInitData() error -> $e")
        }
    }

    /** Navigates to the specific screen module based on the input [AppDataType].
     *
     * @param dataType ([AppDataType] type)
     * */
    private fun loadMoreData(
        dataType: AppDataType
    ) {
        try {
            when (dataType) {
                AppDataType.CHARACTER -> {
                    _uiState.value = HomeUIState.NavigateTo(Screen.CharactersScreen)
                }
                AppDataType.EPISODE -> {
                    _uiState.value = HomeUIState.NavigateTo(Screen.EpisodesScreen)
                }
                AppDataType.LOCATION -> {
                    _uiState.value = HomeUIState.NavigateTo(Screen.LocationsScreen)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "loadMoreData() error -> $e")
        }
    }

    /** Updates and get all the [Character] data, [Episode] data & [Location] data.
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * */
    private fun updateAllData(
        refreshData: Boolean = true
    ) {
        try {
            viewModelScope.launch {
                if (refreshData) {
                    _state.value = state.value.copy(
                        isRefreshing = true
                    )
                }
                updateCharacters(refreshData)
                updateEpisodes(refreshData)
                updateLocations(refreshData)
                _state.value =state.value.copy(
                    isRefreshing = false
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "getInitData() error -> $e")
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
                isScrollingUp = newPosition > lastScrollPosition,
                isScrollUpButtonVisible = newPosition > 0
            )
            lastScrollPosition = newPosition
        } catch (e: Exception) {
            Log.e(TAG, "updateScrollPosition() error -> $e", )
        }
    }

    /** Close / stop / re-start all the [HomeViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getCharactersJob?.cancel()
            getEpisodesJob?.cancel()
            getLocationsJob?.cancel()
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