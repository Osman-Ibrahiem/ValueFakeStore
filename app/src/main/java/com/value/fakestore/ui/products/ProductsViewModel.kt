package com.value.fakestore.ui.products

import androidx.lifecycle.viewModelScope
import com.domain.common.usecase.*
import com.domain.common.usecase.products.GetAllProductsUseCase
import com.domain.common.viewstate.*
import com.domain.common.viewstate.products.ProductsStateResult
import com.domain.core.dispatchers.CoroutineDispatchers
import com.domain.core.viewstate.Loading
import com.value.fakestore.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ProductsViewModel @Inject internal constructor(
    private val dispatchers: CoroutineDispatchers,
    private val getAllProductsUseCase: GetAllProductsUseCase,
) : BaseViewModel<ProductsIntent>() {

    override fun processIntents(intent: ProductsIntent) {
        when (intent) {
            is ProductsIntent.GetAllProducts -> getAllProducts(intent)

        }
    }

    private fun getAllProducts(intent: ProductsIntent.GetAllProducts) {
        viewModelScope.launch {
            getAllProductsUseCase()
                .onStart {
                    emit(Loading())
                }.map { it.apply { type = ProductsStateResult.TYPE } }
                .collect(::sendState)
        }
    }


}