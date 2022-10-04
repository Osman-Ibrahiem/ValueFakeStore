package com.data.remote.mapper

import com.data.remote.model.reponses.ProductRemote
import com.domain.common.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ProductMapper @Inject constructor(
    private val ratingMapper: RatingMapper
) : Mapper<ProductRemote?, Product> {

    override fun mapFromRemote(
        type: ProductRemote?,
    ): Product {
        return Product(
            id = type?.id ?: 0,
            title = type?.title ?: "",
            price = type?.price?.toString()
                ?.replace("0.00", "0")
                ?.replace("0.0", "0")
                ?.replace(".00", "")
                ?.replace(".0", "")
                ?: "0",
            description = type?.description ?: "",
            category = type?.category ?: "",
            image = type?.image ?: "",
            rating = ratingMapper.mapFromRemote(type?.rating),
        )
    }
}
