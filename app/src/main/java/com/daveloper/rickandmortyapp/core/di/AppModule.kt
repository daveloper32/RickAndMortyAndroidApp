package com.daveloper.rickandmortyapp.core.di

import android.app.Application
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.RetrofitUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        app: Application
    ): Retrofit = RetrofitUtils
        .createWebService(
            baseURL = app.getString(R.string.provider_url)
        )
}