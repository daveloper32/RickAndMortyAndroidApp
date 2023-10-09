package com.daveloper.rickandmortyapp.di

import android.app.Application
import androidx.room.PrimaryKey
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase
import com.daveloper.rickandmortyapp.core.utils.room.createARoomDatabase
import com.daveloper.rickandmortyapp.feature_character.data.db.dao.CharacterDao
import com.daveloper.rickandmortyapp.feature_episode.data.db.dao.EpisodeDao
import com.daveloper.rickandmortyapp.feature_location.data.db.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**DI function that provides a singleton of a [RickAndMortyDatabase]*/
    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(
        app: Application
    ): RickAndMortyDatabase = createARoomDatabase<RickAndMortyDatabase>(
        app,
        RickAndMortyDatabase.DATABASE_NAME
    )

    /**DI function that provides a singleton of a [CharacterDao]*/
    @Provides
    @Singleton
    fun provideCharacterDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): CharacterDao = rickAndMortyDatabase.characterDao

    /**DI function that provides a singleton of a [EpisodeDao]*/
    @Provides
    @Singleton
    fun provideEpisodeDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): EpisodeDao = rickAndMortyDatabase.episodeDao

    /**DI function that provides a singleton of a [LocationDao]*/
    @Provides
    @Singleton
    fun provideLocationDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): LocationDao = rickAndMortyDatabase.locationDao
}