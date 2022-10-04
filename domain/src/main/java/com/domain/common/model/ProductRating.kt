package com.domain.common.model

import android.os.Parcelable
import com.domain.core.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductRating(
    var rate: Float,
    var count: Int,
) : BaseModel, Parcelable