package com.daveloper.rickandmortyapp.di

import android.app.Application
import androidx.room.PrimaryKey
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase
import com.daveloper.rickandmortyapp.core.utils.room.createARoomDatabase
import com.daveloper.rickandmortyapp.feature_character.data.db.dao.CharacterDao
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

    @Provides
    @Singleton
    fun provideCharacterDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): CharacterDao = rickAndMortyDatabase.characterDao
}