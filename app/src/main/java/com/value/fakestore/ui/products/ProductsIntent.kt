package com.value.fakestore.ui.products

import com.value.fakestore.base.BaseIntent

sealed class ProductsIntent : BaseIntent {

    object GetAllProducts : ProductsIntent()

}