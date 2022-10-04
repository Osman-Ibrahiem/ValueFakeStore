package com.data.cache.source

import android.content.Context
import android.content.SharedPreferences
import com.domain.core.di.annotations.qualifiers.AppContext
import com.domain.core.di.annotations.qualifiers.AppPreferenceName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceModule @Inject
constructor(@AppContext context: Context, @AppPreferenceName prefName: String) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun registerOnSharedPreferenceChangeListener(): SharedPreferences = preferences

    fun putString(key: String, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putInt(key: String, value: Int?) {
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putInt(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean?) {
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putBoolean(key, value)
        editor.apply()
    }


    fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

}
