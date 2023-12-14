package com.daveloper.rickandmortyapp.feature_episode.presentation.episode_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharactersByIdUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodeByIdUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resourceProvider: ResourceProvider,
    private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    private val getCharactersByIdUseCase: GetCharactersByIdUseCase,
): ViewModel() {
    companion object {
        private val TAG = EpisodeDetailsViewModel::class.java.name
    }
    // View Model connexion state
    private val _state = mutableStateOf(
        EpisodeDetailsState()
    )
    val state: State<EpisodeDetailsState> = _state

    // Job to control the characters flow
    private var getCharactersJob: Job? = null

    init {
        try {
            loadInitData()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Loads the [Episode] data based on the [Screen.EpisodeDetailsScreen.EPISODE_ID_PARAM]
     * input value from the [SavedStateHandle].
     * */
    private fun loadInitData() {
        try {
            savedStateHandle
                .get<Int>(
                    Screen.EpisodeDetailsScreen.EPISODE_ID_PARAM
                )?.let { episodeId ->
                    if (episodeId != Constants.INVALID_INT) {
                        viewModelScope.launch {
                            getEpisodeInfo(episodeId)
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "loadInitData() error -> $e", )
        }
    }

    /** Try to search get the [Episode] data based on the input id and setup the data on screen
     * via [EpisodeDetailsState].
     *
     * @param id ([Int] type)
     * */
    private suspend fun getEpisodeInfo(
        id: Int
    ) {
        try {
            val result = getEpisodeByIdUseCase.invoke(
                id = id
            )
            when (result) {
                is UseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        episode = result.data!!
                    )
                    getCharactersFromEpisode(result.data.characterIds)
                }
                is UseCaseResult.Message -> {
                    // TODO set up message error handling
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodeInfo() error -> $e", )
        }
    }

    /** Get and search all the [Character] where the [Episode] appears found to the view, handling
     * the [GetCharactersByIdUseCase] response ([Flow]<[List]<[Character]>>) to a local [Job] that
     * could be cancelled at any time.
     *
     * @param ids ([List]<[Int]> type)
     * */
    private fun getCharactersFromEpisode(
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

    /** Close / stop / re-start all the [EpisodeDetailsViewModel] controls.
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