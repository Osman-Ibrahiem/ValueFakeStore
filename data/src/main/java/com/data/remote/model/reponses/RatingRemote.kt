package com.data.remote.model.reponses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatingRemote(
    @SerializedName("rate")
    @Expose
    var rate: Float?,
    @SerializedName("count")
    @Expose
    var count: Int? = null,
)
