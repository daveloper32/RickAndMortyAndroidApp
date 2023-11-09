package com.daveloper.rickandmortyapp.feature_location.presentation.locations

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
import com.daveloper.rickandmortyapp.feature_location.domain.GetLocationDimensionsUseCase
import com.daveloper.rickandmortyapp.feature_location.domain.GetLocationTypesUseCase
import com.daveloper.rickandmortyapp.feature_location.domain.GetLocationsUseCase
import com.daveloper.rickandmortyapp.feature_location.domain.UpdateLocationsUseCase
import com.daveloper.rickandmortyapp.feature_location.domain.enums.LocationFilterType
import com.daveloper.rickandmortyapp.feature_location.domain.model.LocationFilter
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateLocationsUseCase: UpdateLocationsUseCase,
    private val getLocationTypesUseCase: GetLocationTypesUseCase,
    private val getLocationDimensionsUseCase: GetLocationDimensionsUseCase,
): ViewModel() {

    companion object {
        private val TAG = LocationsViewModel::class.java.name
    }
    // Location filter selected
    private val locationFilter: LocationFilter = LocationFilter(
        type = resourceProvider.getStringResource(R.string.lab_all),
        dimension = resourceProvider.getStringResource(R.string.lab_all),
    )
    // View Model connexion state
    private val _state = mutableStateOf(
        LocationsState(
            selectedType = locationFilter.type,
            selectedDimension = locationFilter.dimension,
        )
    )
    val state: State<LocationsState> = _state
    // State for handling the search on view
    private val _searchText = mutableStateOf<TextFieldState>(
        TextFieldState(hint = resourceProvider.getStringResource(R.string.lab_search))
    )
    val searchText: State<TextFieldState> get() = _searchText

    // Job to control the locations flow
    private var getLocationsJob: Job? = null
    // Job to control the locations types flow
    private var getLocationTypesJob: Job? = null
    // Job to control the locations dimensions flow
    private var getLocationDimensionsJob: Job? = null

    init {
        try {
            getInitData()
            getLocations()
            getTypesLocations()
            getDimensionLocations()
        } catch (e: Exception) {
            Log.e(TAG, "init error -> $e")
        }
    }

    /** Receive all the events from the view/screen
     * @param event ([LocationsEvent] type)
     * */
    fun onEvent(
        event: LocationsEvent
    ) {
        try {
            when (event) {
                is LocationsEvent.Search -> {
                    _searchText.value = searchText.value.copy(
                        text = event.query
                    )
                    getLocations(event.query)
                }
                is LocationsEvent.ClearSearchBar -> {
                    _searchText.value = searchText.value.copy(
                        text = EMPTY_STR
                    )
                    getLocations()
                }
                is LocationsEvent.ActivateFilter -> {
                    _state.value = state.value.copy(
                        isFilterResumeVisible = !state.value.isFilterResumeVisible,
                        isFilterSelectorVisible = !state.value.isFilterSelectorVisible
                    )
                }
                is LocationsEvent.Filter -> {
                    val currentQuery: String = searchText.value.text
                    when (event.locationFilterType) {
                        LocationFilterType.TYPE -> {
                            locationFilter.type = event.value
                            _state.value = state.value.copy(
                                selectedType = event.value
                            )
                            getTypesLocations()
                        }
                        LocationFilterType.DIMENSION -> {
                            locationFilter.dimension = event.value
                            _state.value = state.value.copy(
                                selectedDimension = event.value
                            )
                            getDimensionLocations()
                        }
                    }
                    getLocations(currentQuery)
                }
                is LocationsEvent.Refresh -> {
                    updateLocations()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent() error -> $e")
        }
    }

    /** Get and send all the [Location] found to the view, handling the [GetLocationsUseCase]
     * response ([Flow]<[List]<[Location]>>) to a local [Job] that could be cancelled at any time.
     *
     * @param searchQuery ([String] type)
     * */
    private fun getLocations(
        searchQuery: String = EMPTY_STR
    ) {
        try {
            getLocationsJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getLocationsJob = getLocationsUseCase
                .invoke(
                    searchQuery = searchQuery,
                    type = locationFilter.type,
                    dimension = locationFilter.dimension
                )
                .onEach { locations ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        locations = locations,
                        isNotFoundDataVisible = locations.isEmpty()
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getLocations() error -> $e")
        }
    }

    /** Get and send all the [Location] types found to the view, handling the
     * [GetLocationTypesUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getTypesLocations(
    ) {
        try {
            getLocationTypesJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getLocationTypesJob = getLocationTypesUseCase
                .invoke()
                .onEach { types ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        types = types.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = locationFilter.type.lowercase() == it.lowercase()
                            )
                        },
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getTypesLocations() error -> $e")
        }
    }

    /** Get and send all the [Location] types found to the view, handling the
     * [GetLocationDimensionsUseCase] response ([Flow]<[List]<[String]>>) to a local [Job] that
     * could be cancelled at any time.
     * */
    private fun getDimensionLocations(
    ) {
        try {
            getLocationDimensionsJob?.cancel() // Cancel the Job on each change to avoid multiple subscriptions
            getLocationDimensionsJob = getLocationDimensionsUseCase
                .invoke()
                .onEach { types ->
                    // With the copy, we retain all the values from current state and modify what we want
                    _state.value = state.value.copy(
                        dimensions = types.map {
                            ItemDataFilter(
                                label = it,
                                isSelected = locationFilter.dimension.lowercase() == it.lowercase()
                            )
                        },
                    )
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, "getDimensionLocations() error -> $e")
        }
    }

    /** Updates and get all the [Location] data, handling the [UpdateLocationsUseCase].
     *
     * @param refreshData ([Boolean] type) -> wants to refresh data?
     * */
    private fun updateLocations(
        refreshData: Boolean = true
    ) {
        try {
            viewModelScope.launch {
                if (refreshData) {
                    _state.value = state.value.copy(
                        isRefreshing = true
                    )
                }
                val result = updateLocationsUseCase.invoke(
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
            Log.e(TAG, "updateLocations() error -> $e")
        }
    }

    /** Gets the initial [Location] data, if for some reason it doesn't have any data on local.
     * */
    private fun getInitData() {
        try {
            updateLocations(false)
        } catch (e: Exception) {
            Log.e(TAG, "getInitData() error -> $e")
        }
    }

    /** Close / stop / re-start all the [LocationsViewModel] controls.
     * */
    private fun stopViewModelControls() {
        try {
            getLocationsJob?.cancel()
            getLocationTypesJob?.cancel()
            getLocationDimensionsJob?.cancel()
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