package com.example.ironsourcetest.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

const val BUNDLE_ACTION_CONFIG = "bundle action config"

class ActionSharedPreferences(context: Context) {

    private val actionConfig: ParameterizedType = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        Long::class.javaObjectType,
    )

    private val moshi = Moshi.Builder().build()
    private val moshiActionConfigAdapter = moshi.adapter<MutableMap<String, Long>>(actionConfig)

    fun setLastShownTimeForAction(type: String) {
        val serializedObject = sharedPref.getString(BUNDLE_ACTION_CONFIG, null)
        if (serializedObject != null) {
            val map = moshiActionConfigAdapter.fromJson(serializedObject)
            map?.put(type, System.currentTimeMillis())
            sharedPref.edit().putString(BUNDLE_ACTION_CONFIG, moshiActionConfigAdapter.toJson(map))
                .apply()
        } else {
            val map = mutableMapOf<String, Long>()
            map.put(type, System.currentTimeMillis())
            sharedPref.edit().putString(BUNDLE_ACTION_CONFIG, moshiActionConfigAdapter.toJson(map))
                .apply()
        }
    }

    fun getLastShownTimeForAction(type: String): Long {
        val serializedObject = sharedPref.getString(BUNDLE_ACTION_CONFIG, null)
        return if (serializedObject != null) {
            val actionLastShownTime = moshiActionConfigAdapter.fromJson(serializedObject)?.get(type)
            actionLastShownTime ?: -1
        } else -1
    }

    private val sharedPref = context.getSharedPreferences(
        "Action Preferences",
        Context.MODE_PRIVATE
    )
}