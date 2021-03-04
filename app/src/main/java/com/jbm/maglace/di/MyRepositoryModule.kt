package com.jbm.maglace.di

import com.jbm.maglace.model.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyRepositoryModule {
    //This will provide the MyRepository instance for hilt injections
    @Provides
    @Singleton
    fun provideRepo(): MyRepository = MyRepository()
}