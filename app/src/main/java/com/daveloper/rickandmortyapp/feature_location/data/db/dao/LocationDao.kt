package com.daveloper.rickandmortyapp.feature_location.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daveloper.rickandmortyapp.feature_location.data.db.model.LocationEntity
import kotlinx.coroutines.flow.Flow
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase

@Dao
interface LocationDao {
    /**Gets a [Flow] of all [LocationEntity] saved on the [RickAndMortyDatabase]
     *
     * @return [Flow]<[List]<[LocationEntity]>>*/
    @Query("SELECT * FROM locationEntity WHERE (name LIKE :searchQuery)")
    fun getLocations(
        searchQuery: String
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
    @Query("SELECT count(*) FROM LocationEntity")
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
}