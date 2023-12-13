package com.daveloper.rickandmortyapp.feature_character.presentation.character_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharacterByIdUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodesByIdUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodesUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.EpisodesViewModel
import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resourceProvider: ResourceProvider,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getEpisodesByIdUseCase: GetEpisodesByIdUseCase,
): ViewModel() {
    companion object {
        private val TAG = CharacterDetailsViewModel::class.java.name
    }
    // View Model connexion state
    private val _state = mutableStateOf(
        CharacterDetailsState()
    )
    val state: State<CharacterDetailsState> = _state

    // Job to control the episodes flow
    private var getEpisodesJob: Job? = null

    init {
        try {
            loadInitData()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Loads the [Character] data based on the [Screen.CharacterDetailsScreen.CHARACTER_ID_PARAM]
     * input value from the [SavedStateHandle].
     * */
    private fun loadInitData() {
        try {
            savedStateHandle
                .get<Int>(
                    Screen.CharacterDetailsScreen.CHARACTER_ID_PARAM
                )?.let { characterId ->
                    if (characterId != Constants.INVALID_INT) {
                        viewModelScope.launch {
                            getCharacterInfo(characterId)
                        }
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "loadInitData() error -> $e", )
        }
    }

    /** Try to search get the [Character] data based on the input id and setup the data on screen
     * via [CharacterDetailsState].
     *
     * @param id ([Int] type)
     * */
    private suspend fun getCharacterInfo(
        id: Int
    ) {
        try {
            val result = getCharacterByIdUseCase.invoke(
                id = id
            )
            when (result) {
                is UseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        character = result.data!!
                    )
                    getEpisodesFromCharacter(result.data.relatedEpisodeIds)
                }
                is UseCaseResult.Message -> {
                    // TODO set up message error handling
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCharacterInfo() error -> $e", )
        }
    }

    /** Get and search all the [Episode] where the [Character] appears found to the view, handling
     * the [GetEpisodesByIdUseCase] response ([Flow]<[List]<[Episode]>>) to a local [Job] that
     * could be cancelled at any time.
     *
     * @param ids ([List]<[Int]> type)
     * */
    private fun getEpisodesFromCharacter(
        ids: List<Int> = emptyList()
    ) {
        try {
            getEpisodesJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getEpisodesJob = getEpisodesByIdUseCase
                .invoke(
                    ids = ids
                )
                .onEach { episodes ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        episodes = episodes,
                        isNotFoundDataVisible = episodes.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodes() error -> $e")
        }
    }

    /** Close / stop / re-start all the [CharacterDetailsViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getEpisodesJob?.cancel()
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