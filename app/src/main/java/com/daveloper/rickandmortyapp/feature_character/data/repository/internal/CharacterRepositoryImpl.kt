package com.daveloper.rickandmortyapp.feature_character.data.repository.internal

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.conversion.PageInfoUtils.toPageInfoData
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.db.dao.CharacterDao
import com.daveloper.rickandmortyapp.feature_character.data.db.model.CharacterEntity
import com.daveloper.rickandmortyapp.feature_character.data.network.CharacterApiService
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.data.CharacterUtils.toCharacterData
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.data.CharacterUtils.toCharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
    private val characterDao: CharacterDao,
    private val resourceProvider: ResourceProvider,
): CharacterRepository {
    companion object {
        private val TAG = CharacterRepository::class.java.name
    }

    override suspend fun getCharacters(
        requiresRefresh: Boolean
    ): RepositoryResult<List<CharacterData>> {
        return try {
            if (requiresRefresh) { // Force to get data from local
                val networkDataRequestResult = getAllCharactersFromApi { _, someData ->
                    saveCharactersOnLocal(someData)
                }
                if (networkDataRequestResult is RepositoryResult.Error) {
                    getAllCharactersFromLocal()
                } else {
                    networkDataRequestResult
                }
            } else { // Tries to get data from local
                if (!isLocalDBEmpty()) {
                    getAllCharactersFromLocal()
                } else {
                    val networkDataRequestResult = getAllCharactersFromApi { _, someData ->
                        saveCharactersOnLocal(someData)
                    }
                    if (networkDataRequestResult is RepositoryResult.Error) {
                        getAllCharactersFromLocal()
                    } else {
                        networkDataRequestResult
                    }
                }
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun getCharactersInRealTime(
        searchQuery: String,
        quantity: Int?
    ): Flow<List<CharacterData>> {
        return try {
            if (quantity == null) {
                characterDao
                    .getCharacters(
                        searchQuery = searchQuery
                    )
                    .mapNotNull { characters ->
                        characters.mapNotNull { character ->
                            character.toCharacterData()
                        }
                    }
            } else {
                characterDao
                    .getCharactersWithLimit(
                        searchQuery = searchQuery,
                        amount = quantity
                    )
                    .mapNotNull { characters ->
                        characters.mapNotNull { character ->
                            character.toCharacterData()
                        }
                    }
            }
        } catch (e: Exception) {
            throw CharacterRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override suspend fun searchCharactersById(
        ids: List<Int>
    ): RepositoryResult<List<CharacterData>> {
        return try {
            val charactersFromLocal: List<CharacterEntity>? = characterDao
                .getCharactersByIds(id = ids.toIntArray())
            if (charactersFromLocal.isNullOrEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                charactersFromLocal.mapNotNull { it.toCharacterData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun getCharacterLifeStatusInRealTime(): Flow<List<String>> {
        return try {
            characterDao
                .getAllStatus()
                .map { data ->
                    data.filter {
                        it.isNotEmpty()
                    }
                }
        } catch (e: Exception) {
            throw CharacterRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override fun getCharacterSpeciesInRealTime(): Flow<List<String>> {
        return try {
            characterDao
                .getAllSpecies()
                .map { data ->
                    data.filter {
                        it.isNotEmpty()
                    }
                }
        } catch (e: Exception) {
            throw CharacterRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override fun getCharacterGendersInRealTime(): Flow<List<String>> {
        return try {
            characterDao
                .getAllGenders()
                .map { data ->
                    data.filter {
                        it.isNotEmpty()
                    }
                }
        } catch (e: Exception) {
            throw CharacterRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    /** Function that gets a list of characters from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    private suspend fun getCharactersFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<CharacterData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw CharacterRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw CharacterRepositoryException
                    .NoInternetConnection()
            }
            val result = apiService.getCharactersByPage(
                page = pageNumber
            ).await()
            if (result.results.isNullOrEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(
                Pair(
                    result.info?.toPageInfoData(),
                    result.results.mapNotNull { it.toCharacterData() }
                )

            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of all characters from the API
     * @param onDataFromSomePage (Lambda nullable [Unit] function type) -> resolver parameters
     * [PageInfoData]? & [List]<[CharacterData]>?
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    private suspend fun getAllCharactersFromApi(
        onDataFromSomePage: ((PageInfoData?, List<CharacterData>?) -> Unit)? = null
    ): RepositoryResult<List<CharacterData>> {
        return try {
            if (!resourceProvider.isConnectedToNetwork()) {
                throw CharacterRepositoryException
                    .NoInternetConnection()
            }
            var page: Int = 1
            val allCharacters: MutableList<CharacterData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getCharactersFromApiByPage(
                    page
                )
                if (resultByPage is RepositoryResult.Success) {
                    onDataFromSomePage?.invoke(resultByPage.data?.first, resultByPage.data?.second)
                    if (!resultByPage.data?.second.isNullOrEmpty()) {
                        allCharacters.addAll(resultByPage.data!!.second)
                    }
                    page = resultByPage.data?.first?.nextPage ?: Constants.INVALID_INT
                } else {
                    page = Constants.INVALID_INT
                }
            }
            if (allCharacters.isEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(allCharacters)
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of characters by ids from the API
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    private suspend fun getCharactersByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<CharacterData>> {
        return try {
            if (ids.isEmpty()) {
                throw CharacterRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw CharacterRepositoryException.NoInternetConnection()
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw CharacterRepositoryException
                    .NoInternetConnection()
            }
            if (ids.size == 1) {
                val result = apiService.getCharacterById(
                    id = ids.first()
                ).await()
                if (result == null) {
                    throw CharacterRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    listOf(
                        result.toCharacterData()
                    ).mapNotNull { it }
                )
            } else {
                val result = apiService.getCharactersById(
                    ids = ids.toStringJoinedWithCommas()
                ).await()
                if (result.isNullOrEmpty()) {
                    throw CharacterRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    result.mapNotNull { it.toCharacterData() }
                )
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of all characters from the Local DB
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    private fun getAllCharactersFromLocal(

    ): RepositoryResult<List<CharacterData>>  {
        return try {
            val charactersFromLocal: List<CharacterEntity>? = characterDao.getCharactersByIds()
            if (charactersFromLocal.isNullOrEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                charactersFromLocal.mapNotNull { it.toCharacterData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of [CharacterData] and tries to save the valid received data on the
     * local DB after a conversion process to [CharacterEntity].
     *
     * @param data ([List]<[CharacterData]>? type)
     * */
    private fun saveCharactersOnLocal(
        data: List<CharacterData>?
    ) {
        try {
            data?.let { nonNullData ->
                val dataToSave: List<CharacterEntity> = nonNullData.mapNotNull {
                    it.toCharacterEntity()
                }
                characterDao.insertCharacters(dataToSave)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /** Function that valid if the local DB do not have any [CharacterEntity] record
     * @return [Boolean]
     * */
    private fun isLocalDBEmpty(): Boolean = characterDao.getCharactersTotal() == 0
}