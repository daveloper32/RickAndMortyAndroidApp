package com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class EpisodeData(
    val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val airDate: String = Constants.EMPTY_STR,
    val seasonNumber: Int = Constants.INVALID_INT,
    val episodeNumber: Int = Constants.INVALID_INT,
    val episodeStamp: String = Constants.EMPTY_STR,
    val characterIds: List<Int> = emptyList(),
    val url: String = Constants.EMPTY_STR,
    val created: String = Constants.EMPTY_STR,
)
