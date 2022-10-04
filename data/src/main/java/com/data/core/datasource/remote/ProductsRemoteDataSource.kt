package com.data.core.datasource.remote

import com.domain.common.model.Product

interface ProductsRemoteDataSource {

    suspend fun getAllProducts(): List<Product>


}
