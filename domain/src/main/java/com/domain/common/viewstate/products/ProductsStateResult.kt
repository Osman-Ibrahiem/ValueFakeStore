package com.domain.common.viewstate.products

import com.domain.common.model.Product
import com.domain.core.viewstate.BaseVS

class ProductsStateResult(
    val products: List<Product>,
) : BaseVS() {

    companion object {
        const val TYPE = 201
    }
}