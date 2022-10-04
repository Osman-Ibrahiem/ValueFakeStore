package com.data.remote.model.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone_number") @Expose var phone: String? = null,
    @SerializedName("firebase_token") @Expose var firebaseToken: String? = null,
)
