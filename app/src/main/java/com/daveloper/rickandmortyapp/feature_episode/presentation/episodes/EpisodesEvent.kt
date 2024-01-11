package com.daveloper.rickandmortyapp.feature_episode.presentation.episodes

import com.daveloper.rickandmortyapp.feature_episode.domain.enums.EpisodeFilterType

/** The [EpisodesEvent] describe all the possible events that could be launched by
 * [EpisodesViewModel].
 * */
sealed class EpisodesEvent {
    data class Search(
        val query: String
    ): EpisodesEvent()

    object ClearSearchBar: EpisodesEvent()

    object ActivateFilter: EpisodesEvent()

    data class Filter(
        val episodeFilterType: EpisodeFilterType,
        val value: String
    ): EpisodesEvent()

    object Refresh: EpisodesEvent()

    data class ScrollPosition(
        val newPosition: Int
    ): EpisodesEvent()
}