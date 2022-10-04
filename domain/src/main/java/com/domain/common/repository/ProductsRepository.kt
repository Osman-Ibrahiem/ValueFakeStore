package com.domain.common.repository

import com.domain.core.repository.Repository
import com.domain.core.viewstate.BaseVS
import kotlinx.coroutines.flow.Flow

interface ProductsRepository : Repository {

    fun getAllProducts(): Flow<BaseVS>


}