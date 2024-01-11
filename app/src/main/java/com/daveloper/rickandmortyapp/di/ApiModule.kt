package com.daveloper.rickandmortyapp.di

import android.app.Application
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.retrofit.RetrofitUtils
import com.daveloper.rickandmortyapp.feature_character.data.network.CharacterApiService
import com.daveloper.rickandmortyapp.feature_episode.data.network.EpisodeApiService
import com.daveloper.rickandmortyapp.feature_location.data.network.LocationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    /**DI function that provides a singleton of a [CharacterApiService]*/
    @Provides
    @Singleton
    fun provideCharacterApiService(
        app: Application
    ): CharacterApiService = RetrofitUtils
        .createWebService<CharacterApiService>(
            baseURL = app.getString(R.string.provider_url)
        )

    /**DI function that provides a singleton of a [EpisodeApiService]*/
    @Provides
    @Singleton
    fun provideEpisodeApiService(
        app: Application
    ): EpisodeApiService = RetrofitUtils
        .createWebService<EpisodeApiService>(
            baseURL = app.getString(R.string.provider_url)
        )

    /**DI function that provides a singleton of a [LocationApiService]*/
    @Provides
    @Singleton
    fun provideLocationApiService(
        app: Application
    ): LocationApiService = RetrofitUtils
        .createWebService<LocationApiService>(
            baseURL = app.getString(R.string.provider_url)
        )
}