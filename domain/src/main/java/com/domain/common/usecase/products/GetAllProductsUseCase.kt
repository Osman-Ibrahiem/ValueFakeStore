package com.domain.common.usecase.products

import com.domain.common.repository.ProductsRepository
import com.domain.core.dispatchers.CoroutineDispatchers
import com.domain.core.viewstate.BaseVS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetAllProductsUseCase @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val repository: ProductsRepository,
) {

    operator fun invoke(
    ): Flow<BaseVS> {
        return repository.getAllProducts().flowOn(dispatchers.io)
    }
}