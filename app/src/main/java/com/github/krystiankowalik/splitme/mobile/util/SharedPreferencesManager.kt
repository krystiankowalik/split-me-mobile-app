package com.github.krystiankowalik.splitme.mobile.util


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.github.krystiankowalik.authenticationapp.R

class SharedPreferencesManager(val context: Context) {

    private val editor: SharedPreferences.Editor = context.getSharedPreferences(context.getString(R.string.shared_prefs), 0).edit()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefs), Context.MODE_PRIVATE)

    fun write(key: String, string: String) {

        editor.putString(key, string)
        editor.apply()

        val savedValue = sharedPreferences.getString(key,
                context.getString(R.string.no_value))
        Log.e("Saved ", savedValue + " under key: " + key)
    }

    fun read(key: String): String {
        val defVal = context.getString(R.string.no_value)
        return sharedPreferences.getString(key, defVal)

    }
}