package com.daveloper.rickandmortyapp.feature_character.presentation.character_details

/** The [CharacterDetailsEvent] describe all the possible events that could be launched by
 * [CharacterDetailsScreen].
 * */
sealed class CharacterDetailsEvent {
    object OnBack: CharacterDetailsEvent()
}
