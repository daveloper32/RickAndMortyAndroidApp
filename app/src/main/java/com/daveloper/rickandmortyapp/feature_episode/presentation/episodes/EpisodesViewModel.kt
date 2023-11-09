package com.daveloper.rickandmortyapp.feature_episode.presentation.episodes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.ui.components.states.TextFieldState
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodeSeasonsUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.GetEpisodesUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.UpdateEpisodesUseCase
import com.daveloper.rickandmortyapp.feature_episode.domain.enums.EpisodeFilterType
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_episode.domain.model.EpisodeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val updateEpisodesUseCase: UpdateEpisodesUseCase,
    private val getEpisodeSeasonsUseCase: GetEpisodeSeasonsUseCase,
): ViewModel() {
    companion object {
        private val TAG = EpisodesViewModel::class.java.name
    }
    // Episode filter selected
    private val episodeFilter: EpisodeFilter = EpisodeFilter(
        season = resourceProvider.getStringResource(R.string.lab_all),
    )
    // View Model connexion state
    private val _state = mutableStateOf(
        EpisodesState(
            selectedSeason = episodeFilter.season
        )
    )
    val state: State<EpisodesState> = _state
    // State for handling the search on view
    private val _searchText = mutableStateOf<TextFieldState>(
        TextFieldState(hint = resourceProvider.getStringResource(R.string.lab_search))
    )
    val searchText: State<TextFieldState> get() = _searchText

    // Job to control the episodes flow
    private var getEpisodesJob: Job? = null
    // Job to control the episode seasons flow
    private var getEpisodeSeasonsJob: Job? = null

    init {
        try {
            getInitData()
            getEpisodes()
            getSeasonsEpisode()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Receive all the events from the view/screen
     * @param event ([EpisodesEvent] type)
     * */
    fun onEvent(
        event: EpisodesEvent
    ) {
        try {
            when (event) {
                is EpisodesEvent.Search -> {
                    _searchText.value = searchText.value.copy(
                        text = event.query
                    )
                    getEpisodes(event.query)
                }
                is EpisodesEvent.ClearSearchBar -> {
                    _searchText.value = searchText.value.copy(
                        text = EMPTY_STR
                    )
                    getEpisodes()
                }
                is EpisodesEvent.ActivateFilter -> {
                    _state.value = state.value.copy(
                        isFilterResumeVisible = !state.value.isFilterResumeVisible,
                        isFilterSelectorVisible = !state.value.isFilterSelectorVisible
                    )
                }
                is EpisodesEvent.Filter -> {
                    val currentQuery: String = searchText.value.text
                    when (event.episodeFilterType) {
                        EpisodeFilterType.SEASON -> {
                            episodeFilter.season = event.value
                            _state.value = state.value.copy(
                                selectedSeason = event.value
                            )
                            getSeasonsEpisode()
                        }
                    }
                    getEpisodes(currentQuery)
                }
                is EpisodesEvent.Refresh -> {
                    updateEpisodes()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent() error -> $e")
        }
    }

    /** Get and send all the [Episode] found to the view, handling the [GetEpisodesUseCase]
     * response ([Flow]<[List]<[Episode]>>) to a local [Job] that could be cancelled at any time.
     *
     * @param searchQuery ([String] type)
     * */
    private fun getEpisodes(
        searchQuery: String = EMPTY_STR
    ) {
        try {
            getEpisodesJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getEpisodesJob = getEpisodesUseCase
                .invoke(
                    searchQuery = searchQuery,
                    season = episodeFilter.season
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

    /** Get and send all the [Episode] life status found to the view, handling the
     * [GetEpisodeSeasonsUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getSeasonsEpisode(
    ) {
        try {
            getEpisodeSeasonsJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getEpisodeSeasonsJob = getEpisodeSeasonsUseCase
                .invoke()
                .onEach { season ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        season = season.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = episodeFilter.season.lowercase() == it.lowercase()
                            )
                        }
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getEpisodes() error -> $e")
        }
    }

    /** Updates and get all the [Episode] data, handling the [UpdateEpisodesUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * */
    private fun updateEpisodes(
        refreshData: Boolean = true
    ) {
        try {
            viewModelScope.launch {
                if (refreshData) {
                    _state.value = state.value.copy(
                        isRefreshing = true
                    )
                }
                val result = updateEpisodesUseCase.invoke(
                    forceDataRefresh = refreshData
                )
                _state.value =state.value.copy(
                    isRefreshing = false
                )
                when (result) {
                    is UseCaseResult.Success -> {

                    }
                    is UseCaseResult.Message -> {

                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateEpisodes() error -> $e")
        }
    }

    /** Gets the initial [Episode] data, if for some reason it doesn't have any data on local.
     * */
    private fun getInitData() {
        try {
            updateEpisodes(false)
        } catch (e: Exception) {
            Log.e(TAG, "getInitData() error -> $e")
        }
    }

    /** Close / stop / re-start all the [EpisodesViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getEpisodesJob?.cancel()
            getEpisodeSeasonsJob?.cancel()
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