package com.daveloper.rickandmortyapp.di

import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.data.repository.internal.CharacterRepositoryImpl
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.data.repository.internal.EpisodeRepositoryImpl
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.data.repository.internal.LocationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**DI function that provides a singleton of a [CharacterRepository]*/
    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository = characterRepositoryImpl

    /**DI function that provides a singleton of a [EpisodeRepository]*/
    @Provides
    @Singleton
    fun provideEpisodeRepository(
        episodeRepositoryImpl: EpisodeRepositoryImpl
    ): EpisodeRepository = episodeRepositoryImpl

    /**DI function that provides a singleton of a [LocationRepository]*/
    @Provides
    @Singleton
    fun provideLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository = locationRepositoryImpl
}