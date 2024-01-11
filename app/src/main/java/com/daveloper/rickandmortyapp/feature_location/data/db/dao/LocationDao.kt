package com.daveloper.rickandmortyapp.feature_location.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase
import com.daveloper.rickandmortyapp.feature_location.data.db.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    /**Gets a [Flow] of all [LocationEntity] saved on the [RickAndMortyDatabase]
     *
     * @param searchQuery ([String] type) - Filter results by [LocationEntity] name, if the query
     * is empty, it returns all the data found in the DB.
     * @return [Flow]<[List]<[LocationEntity]>>*/
    @Query("SELECT * FROM locationEntity WHERE (:searchQuery = '' OR name LIKE '%' || :searchQuery || '%')")
    fun getLocations(
        searchQuery: String
    ): Flow<List<LocationEntity>>

    /**Gets a [Flow] of all [LocationEntity] saved on the [RickAndMortyDatabase]
     *
     * @param searchQuery ([String] type) - Filter results by [LocationEntity] name, if the query
     * is empty, it returns all the data found in the DB.
     * @param amount ([Int] type) - Gets just some results.
     * @return [Flow]<[List]<[LocationEntity]>>*/
    @Query("SELECT * FROM locationEntity WHERE (:searchQuery = '' OR name LIKE '%' || :searchQuery || '%') LIMIT :amount")
    fun getLocationsWithLimit(
        searchQuery: String,
        amount: Int
    ): Flow<List<LocationEntity>>

    /**Search and get a [LocationEntity] that matches with the input id from the
     * [RickAndMortyDatabase].
     *
     * If is not found returns null.
     *
     * @param id ([Int] type)
     * @return [LocationEntity]?
     * */
    @Query("SELECT * FROM locationEntity WHERE id = :id")
    suspend fun getLocationById(
        id: Int
    ): LocationEntity?

    /**Search and get all the [LocationEntity] that matches with the input ids from the
     * [RickAndMortyDatabase].
     *
     * @param id (vararg [Int] type)
     * @return [List]<[LocationEntity]>?
     * */
    @Query("SELECT * FROM locationEntity WHERE id IN (:id)")
    fun getLocationsByIds(
        vararg id: Int
    ): List<LocationEntity>?

    /**Insert a [LocationEntity] on the [RickAndMortyDatabase].
     *
     * @param Location ([LocationEntity] type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertLocation(
        location: LocationEntity
    )

    /**Insert a [List] of [LocationEntity] on the [RickAndMortyDatabase].
     *
     * @param Locations ([List]<[LocationEntity]> type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertLocations(
        locations: List<LocationEntity>
    )

    /** Gets the total of [LocationEntity] saved on the [RickAndMortyDatabase].
     *
     * @return [Int]?
     * */
    @Query("SELECT count(*) FROM locationEntity")
    fun getLocationsTotal(): Int?

    /** Deletes an input [LocationEntity] from the [RickAndMortyDatabase].
     *
     * @param Location ([LocationEntity])
     * */
    @Delete
    fun deleteLocation(
        location: LocationEntity
    )

    /** Deletes an all the [LocationEntity] from the [RickAndMortyDatabase].
     * */
    @Query("DELETE FROM locationEntity")
    fun deleteAllLocations()

    /** Filter the [LocationEntity] table and gets all the unique type ([String]) values
     * @return [Flow]<[List]<[String]>>
     * */
    @Query("SELECT DISTINCT type FROM locationEntity")
    fun getAllTypes(): Flow<List<String>>

    /** Filter the [LocationEntity] table and gets all the unique dimension ([String]) values
     * @return [Flow]<[List]<[String]>>
     * */
    @Query("SELECT DISTINCT dimension FROM locationEntity")
    fun getAllDimensions(): Flow<List<String>>
}