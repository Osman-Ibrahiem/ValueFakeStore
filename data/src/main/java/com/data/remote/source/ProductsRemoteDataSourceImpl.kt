package com.data.remote.source

import com.data.core.datasource.remote.ProductsRemoteDataSource
import com.data.remote.mapper.ProductMapper
import com.data.remote.service.ProductsAPIService
import com.domain.common.model.Product
import com.domain.core.RemoteException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsAPIService: ProductsAPIService,
    private val productMapper: ProductMapper,
) : ProductsRemoteDataSource {

    override suspend fun getAllProducts(): List<Product> {
        val request = productsAPIService.getAllProducts()
        val body = request.body()
        return if (request.isSuccessful && body != null) {
            body.map(productMapper::mapFromRemote)
        } else throw RemoteException(request.code(), request.errorBody()?.string() ?: "")
    }

}