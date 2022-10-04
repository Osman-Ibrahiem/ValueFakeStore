package com.value.fakestore.di

import com.data.remote.service.ProductsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    fun provideProductsApiService(retrofit: Retrofit): ProductsAPIService = ProductsAPIService(retrofit)

}