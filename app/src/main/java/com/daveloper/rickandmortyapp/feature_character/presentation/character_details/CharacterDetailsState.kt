package com.daveloper.rickandmortyapp.feature_character.presentation.character_details

import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersState
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.CharactersViewModel

/**The [CharactersState] describes all [CharactersViewModel] view/screen values.
 *
 * @param character ([Character] type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * */
data class CharacterDetailsState(
    var character: Character = Character(),
    val isNotFoundDataVisible: Boolean = false,
)
