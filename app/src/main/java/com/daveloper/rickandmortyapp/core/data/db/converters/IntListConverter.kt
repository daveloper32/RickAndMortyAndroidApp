package com.daveloper.rickandmortyapp.core.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntListConverter {
    @TypeConverter
    fun fromIntList(
        value: List<Int>
    ): String {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntList(
        value: String
    ): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}