package com.daveloper.rickandmortyapp.feature_character.presentation.characters

import com.daveloper.rickandmortyapp.feature_character.domain.enums.CharacterFilterType

/** The [CharactersEvent] describe all the possible events that could be launched by
 * [CharactersScreen].
 * */
sealed class CharactersEvent {
    data class Search(
        val query: String
    ): CharactersEvent()

    object ClearSearchBar: CharactersEvent()

    object ActivateFilter: CharactersEvent()

    data class Filter(
        val characterFilterType: CharacterFilterType,
        val value: String
    ): CharactersEvent()

    object Refresh: CharactersEvent()

    data class ScrollPosition(
        val newPosition: Int
    ): CharactersEvent()
}
