package com.data.remote.mapper

import com.data.remote.model.reponses.RatingRemote
import com.domain.common.model.ProductRating
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class RatingMapper @Inject constructor() : Mapper<RatingRemote?, ProductRating> {

    override fun mapFromRemote(
        type: RatingRemote?,
    ): ProductRating {
        return ProductRating(
            rate = type?.rate ?: 0f,
            count = type?.count ?: 0,
        )
    }
}
