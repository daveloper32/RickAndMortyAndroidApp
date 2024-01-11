package com.daveloper.rickandmortyapp.feature_episode.domain.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class EpisodeFilter(
    var season: String = Constants.EMPTY_STR
)
