package com.value.fakestore.di

import com.data.core.datasource.remote.ProductsRemoteDataSource
import com.data.core.repository.ProductsRepositoryImpl
import com.data.remote.source.ProductsRemoteDataSourceImpl
import com.domain.common.repository.ProductsRepository
import com.domain.core.dispatchers.CoroutineDispatchers
import com.domain.core.dispatchers.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindCoroutineDispatchers(coroutineDispatchersImpl: CoroutineDispatchersImpl): CoroutineDispatchers

    @Binds
    abstract fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    abstract fun bindProductsRemoteDataSource(productsRemoteDataSourceImpl: ProductsRemoteDataSourceImpl): ProductsRemoteDataSource

}
