package com.data.cache.source

import javax.inject.Inject

class UserModule @Inject constructor(
    private val preferenceModule: PreferenceModule,
) {

    var isLogin: Boolean
        get() = preferenceModule.getBoolean("_saved_login", false)
        set(value) {
            preferenceModule.putBoolean("_saved_login", value)
            if (!value) {
                token = null
            }
        }

    var isFirstTime: Boolean
        get() = preferenceModule.getBoolean("_first_time", true)
        set(value) {
            preferenceModule.putBoolean("_first_time", value)
        }

    var token: String?
        set(value) {
            if (value.isNullOrEmpty()) {
                preferenceModule.putString("_saved_token_", null)
            } else preferenceModule.putString("_saved_token_", value)
        }
        get() {
            val savedValue = preferenceModule.getString("_saved_token_", null)
            if (savedValue.isNullOrEmpty()) return null
            return savedValue
        }

    var firebaseToken: String?
        set(value) {
            if (value.isNullOrEmpty()) {
                preferenceModule.putString("_firebase_token_", null)
            } else preferenceModule.putString("_firebase_token_", value)
        }
        get() {
            val savedValue = preferenceModule.getString("_firebase_token_", null)
            if (savedValue.isNullOrEmpty()) return null
            return savedValue
        }

}