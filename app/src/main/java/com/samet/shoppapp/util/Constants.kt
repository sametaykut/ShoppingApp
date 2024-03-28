package com.samet.shoppapp.util

import android.content.Context

class Constants {


    companion object{

        val BASE_URL = "https://fakestoreapi.com/"
        val PREF_NAME = "MyPreferences"

        fun saveInt(context: Context, key: String, value: Int) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getInt(context: Context, key: String, defaultValue: Int): Int {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt(key, defaultValue) ?: defaultValue
        }
    }

}