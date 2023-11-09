package com.daveloper.rickandmortyapp.feature_character.presentation.characters

import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character

/**The [CharactersState] describes all [CharactersViewModel] view/screen values.
 *
 * @param isFilterResumeVisible ([Boolean] type)
 * @param isFilterSelectorVisible ([Boolean] type)
 * @param characters ([List]<[Character]> type)
 * @param lifeStatus ([List]<[ItemDataFilter]> type)
 * @param species ([List]<[ItemDataFilter]> type)
 * @param genders ([List]<[ItemDataFilter]> type)
 * @param selectedLifeStatus ([String] type)
 * @param selectedSpecies ([String] type)
 * @param selectedGender ([String] type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * @param isRefreshing ([Boolean] type)
 * */
data class CharactersState(
    val isFilterResumeVisible: Boolean = true,
    val isFilterSelectorVisible: Boolean = false,
    val characters: List<Character> = emptyList(),
    val lifeStatus: List<ItemDataFilter> = emptyList(),
    val species: List<ItemDataFilter> = emptyList(),
    val genders: List<ItemDataFilter> = emptyList(),
    val selectedLifeStatus: String = EMPTY_STR,
    val selectedSpecies: String = EMPTY_STR,
    val selectedGender: String = EMPTY_STR,
    val isNotFoundDataVisible: Boolean = false,
    val isRefreshing: Boolean = false,
)
