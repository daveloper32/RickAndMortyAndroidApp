package com.daveloper.rickandmortyapp.feature_episode.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase
import com.daveloper.rickandmortyapp.feature_episode.data.db.model.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    /**Gets a [Flow] of all [EpisodeEntity] saved on the [RickAndMortyDatabase]
     *
     * @param searchQuery ([String] type) - Filter results by [EpisodeEntity] name, if the query
     * is empty, it returns all the data found in the DB.
     * @return [Flow]<[List]<[EpisodeEntity]>>*/
    @Query("SELECT * FROM episodeEntity WHERE (:searchQuery = '' OR name LIKE '%' || :searchQuery || '%')")
    fun getEpisodes(
        searchQuery: String
    ): Flow<List<EpisodeEntity>>

    /**Search and get a [EpisodeEntity] that matches with the input id from the
     * [RickAndMortyDatabase].
     *
     * If is not found returns null.
     *
     * @param id ([Int] type)
     * @return [EpisodeEntity]?
     * */
    @Query("SELECT * FROM episodeEntity WHERE id = :id")
    suspend fun getEpisodeById(
        id: Int
    ): EpisodeEntity?

    /**Search and get all the [EpisodeEntity] that matches with the input ids from the
     * [RickAndMortyDatabase].
     *
     * @param id (vararg [Int] type)
     * @return [List]<[EpisodeEntity]>?
     * */
    @Query("SELECT * FROM episodeEntity WHERE id IN (:id)")
    fun getEpisodesByIds(
        vararg id: Int
    ): List<EpisodeEntity>?

    /**Insert a [EpisodeEntity] on the [RickAndMortyDatabase].
     *
     * @param episode ([EpisodeEntity] type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertEpisode(
        episode: EpisodeEntity
    )

    /**Insert a [List] of [EpisodeEntity] on the [RickAndMortyDatabase].
     *
     * @param episodes ([List]<[EpisodeEntity]> type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertEpisodes(
        episodes: List<EpisodeEntity>
    )

    /** Gets the total of [EpisodeEntity] saved on the [RickAndMortyDatabase].
     *
     * @return [Int]?
     * */
    @Query("SELECT count(*) FROM episodeEntity")
    fun getEpisodesTotal(): Int?

    /** Deletes an input [EpisodeEntity] from the [RickAndMortyDatabase].
     *
     * @param episode ([EpisodeEntity])
     * */
    @Delete
    fun deleteEpisode(
        episode: EpisodeEntity
    )

    /** Deletes an all the [EpisodeEntity] from the [RickAndMortyDatabase].
     * */
    @Query("DELETE FROM episodeEntity")
    fun deleteAllEpisodes()

    /** Filter the [EpisodeEntity] table and gets all the unique season ([Int]) values
     * @return [Flow]<[List]<[Int]>>
     * */
    @Query("SELECT DISTINCT seasonNumber FROM episodeEntity")
    fun getAllSeasons(): Flow<List<Int>>
}