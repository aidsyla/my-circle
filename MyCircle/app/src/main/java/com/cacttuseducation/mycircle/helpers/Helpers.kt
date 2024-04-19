package com.cacttuseducation.mycircle.helpers

import android.content.Context
import android.content.SharedPreferences
import com.cacttuseducation.mycircle.api.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Helpers {

    fun provideRetrofitInstance(): API {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.117:8080/api/")
            .build().create(API::class.java)
    }

    private fun provideSharedPrefsInstance(context: Context): SharedPreferences {
        return context.getSharedPreferences("apps_shared_prefs", Context.MODE_PRIVATE)
    }

    fun putStringOnSharedPrefs(context: Context, key: String, value: String) {
        provideSharedPrefsInstance(context)
        provideSharedPrefsInstance(context).edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getStringFromSharedPrefs(context: Context, key: String): String?{
        return provideSharedPrefsInstance(context).getString(key, null)
    }
}