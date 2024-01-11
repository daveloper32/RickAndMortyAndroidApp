package com.daveloper.rickandmortyapp.feature_episode.presentation.episode_details

import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode

/**The [EpisodeDetailsState] describes all [EpisodeDetailsViewModel] view/screen values.
 *
 * @param episode ([Episode] type)
 * @param characters ([List]<[Character]> type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * */
data class EpisodeDetailsState(
    var episode: Episode = Episode(),
    var characters: List<Character> = emptyList(),
    val isNotFoundDataVisible: Boolean = false,
)