package com.data.core.repository

import com.data.cache.source.UserModule
import com.data.core.datasource.remote.ProductsRemoteDataSource
import com.domain.common.repository.ProductsRepository
import com.domain.common.viewstate.products.*
import com.domain.core.Constants
import com.domain.core.viewstate.BaseVS
import com.domain.core.viewstate.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val userModule: UserModule,
) : ProductsRepository {

    override fun getAllProducts(): Flow<BaseVS> = flow {
        try {
//            withTimeout(Constants.NETWORK_TIMEOUT) {
                val products = productsRemoteDataSource.getAllProducts()

                emit(ProductsStateResult(products))
//            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            println("new Error ${throwable.message}")
            emit(Failure.get(error = throwable))
        }
    }

}
