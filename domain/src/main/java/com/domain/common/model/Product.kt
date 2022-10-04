package com.domain.common.model

import android.os.Parcelable
import com.domain.core.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: Int,
    var title: String,
    var price: String,
    var description: String,
    var category: String,
    var image: String,
    var rating: ProductRating,
) : BaseModel, Parcelable