package com.daveloper.rickandmortyapp.di

import android.app.Application
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.retrofit.RetrofitUtils
import com.daveloper.rickandmortyapp.feature_character.data.network.CharacterApiService
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.data.repository.internal.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**DI function that provides a singleton of a [CharacterApiService]*/
    @Provides
    @Singleton
    fun provideCharacterApiService(
        app: Application
    ): CharacterApiService = RetrofitUtils
        .createWebService<CharacterApiService>(
            baseURL = app.getString(R.string.provider_url)
        )

    /**DI function that provides a singleton of a [CharacterRepository]*/
    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository = characterRepositoryImpl
}