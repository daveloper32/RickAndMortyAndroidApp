package com.daveloper.rickandmortyapp.feature_character.presentation.characters

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
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharacterGendersUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharacterLifeStatusUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharacterSpeciesUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharactersUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.UpdateCharactersUseCase
import com.daveloper.rickandmortyapp.feature_character.domain.enums.CharacterFilterType
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.domain.model.CharacterFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val updateCharactersUseCase: UpdateCharactersUseCase,
    private val getCharacterLifeStatusUseCase: GetCharacterLifeStatusUseCase,
    private val getCharacterSpeciesUseCase: GetCharacterSpeciesUseCase,
    private val getCharacterGendersUseCase: GetCharacterGendersUseCase,
): ViewModel() {
    companion object {
        private val TAG = CharactersViewModel::class.java.name
    }
    // Character filter selected
    private val characterFilter: CharacterFilter = CharacterFilter(
        lifeStatus = resourceProvider.getStringResource(R.string.lab_all),
        species = resourceProvider.getStringResource(R.string.lab_all),
        gender = resourceProvider.getStringResource(R.string.lab_all),
    )
    // View Model connexion state
    private val _state = mutableStateOf(
        CharactersState(
            selectedLifeStatus = characterFilter.lifeStatus,
            selectedSpecies = characterFilter.species,
            selectedGender = characterFilter.gender,
        )
    )
    val state: State<CharactersState> = _state
    // State for handling the search on view
    private val _searchText = mutableStateOf<TextFieldState>(
        TextFieldState(hint = resourceProvider.getStringResource(R.string.lab_search))
    )
    val searchText: State<TextFieldState> get() = _searchText

    // Job to control the characters flow
    private var getCharactersJob: Job? = null
    // Job to control the character life status flow
    private var getCharacterLifeStatusJob: Job? = null
    // Job to control the character species flow
    private var getCharacterSpeciesJob: Job? = null
    // Job to control the character genders flow
    private var getCharacterGendersJob: Job? = null

    init {
        try {
            getInitData()
            getCharacters()
            getLifeStatusCharacters()
            getSpeciesCharacters()
            getGenderCharacters()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Receive all the events from the view/screen
     * @param event ([CharactersEvent] type)
     * */
    fun onEvent(
        event: CharactersEvent
    ) {
        try {
            when (event) {
                is CharactersEvent.Search -> {
                    _searchText.value = searchText.value.copy(
                        text = event.query
                    )
                    getCharacters(event.query)
                }
                is CharactersEvent.ClearSearchBar -> {
                    _searchText.value = searchText.value.copy(
                        text = EMPTY_STR
                    )
                    getCharacters()
                }
                is CharactersEvent.ActivateFilter -> {
                    _state.value = state.value.copy(
                        isFilterResumeVisible = !state.value.isFilterResumeVisible,
                        isFilterSelectorVisible = !state.value.isFilterSelectorVisible
                    )
                }
                is CharactersEvent.Filter -> {
                    val currentQuery: String = searchText.value.text
                    when (event.characterFilterType) {
                        CharacterFilterType.LIFE_STATUS -> {
                            characterFilter.lifeStatus = event.value
                            _state.value = state.value.copy(
                                selectedLifeStatus = event.value
                            )
                            getLifeStatusCharacters()
                        }
                        CharacterFilterType.SPECIES -> {
                            characterFilter.species = event.value
                            _state.value = state.value.copy(
                                selectedSpecies = event.value
                            )
                            getSpeciesCharacters()
                        }
                        CharacterFilterType.GENDER -> {
                            characterFilter.gender = event.value
                            _state.value = state.value.copy(
                                selectedGender = event.value
                            )
                            getGenderCharacters()
                        }
                    }
                    getCharacters(currentQuery)
                }
                is CharactersEvent.Refresh -> {
                    updateCharacters()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent() error -> $e")
        }
    }

    /** Get and send all the [Character] found to the view, handling the [GetCharactersUseCase]
     * response ([Flow]<[List]<[Character]>>) to a local [Job] that could be cancelled at any time.
     *
     * @param searchQuery ([String] type)
     * */
    private fun getCharacters(
        searchQuery: String = EMPTY_STR
    ) {
        try {
            Log.i(TAG, "getCharacters() called")
            _state.value = state.value.copy(
                isNotFoundDataVisible = true
            )
            getCharactersJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharactersJob = getCharactersUseCase
                .invoke(
                    searchQuery = searchQuery,
                    lifeStatus = characterFilter.lifeStatus,
                    species = characterFilter.species,
                    gender = characterFilter.gender
                )
                .onEach { characters ->
                    Log.i(TAG, "getCharacters() answer")
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        characters = characters,
                        isNotFoundDataVisible = characters.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getCharacters() error -> $e")
        }
    }

    /** Get and send all the [Character] life status found to the view, handling the
     * [GetCharacterLifeStatusUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getLifeStatusCharacters(
    ) {
        try {
            getCharacterLifeStatusJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharacterLifeStatusJob = getCharacterLifeStatusUseCase
                .invoke()
                .onEach { lifeStatus ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        lifeStatus = lifeStatus.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = characterFilter.lifeStatus.lowercase() == it.lowercase()
                            )
                        },
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getLifeStatusCharacters() error -> $e")
        }
    }

    /** Get and send all the [Character] species found to the view, handling the
     * [GetCharacterSpeciesUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getSpeciesCharacters(
    ) {
        try {
            getCharacterSpeciesJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharacterSpeciesJob = getCharacterSpeciesUseCase
                .invoke()
                .onEach { species ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        species = species.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = characterFilter.species.lowercase() == it.lowercase()
                            )
                        },
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getSpeciesCharacters() error -> $e")
        }
    }

    /** Get and send all the [Character] genders found to the view, handling the
     * [GetCharacterGendersUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getGenderCharacters(
    ) {
        try {
            getCharacterGendersJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getCharacterGendersJob = getCharacterGendersUseCase
                .invoke()
                .onEach { genders ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        genders = genders.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = characterFilter.gender.lowercase() == it.lowercase()
                            )
                        },
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getGenderCharacters() error -> $e")
        }
    }

    /** Updates and get all the [Character] data, handling the [UpdateCharactersUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * */
    private fun updateCharacters(
        refreshData: Boolean = true
    ) {
        try {
            viewModelScope.launch {
                if (refreshData) {
                    _state.value = state.value.copy(
                        isRefreshing = true
                    )
                }
                val result = updateCharactersUseCase.invoke(
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
            Log.e(TAG, "updateCharacters() error -> $e")
        }
    }

    /** Gets the initial [Character] data, if for some reason it doesn't have any data on local.
     * */
    private fun getInitData() {
        try {
            updateCharacters(false)
        } catch (e: Exception) {
            Log.e(TAG, "getInitData() error -> $e")
        }
    }

    /** Close / stop / re-start all the [CharactersViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getCharactersJob?.cancel()
            getCharacterLifeStatusJob?.cancel()
            getCharacterSpeciesJob?.cancel()
            getCharacterGendersJob?.cancel()
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