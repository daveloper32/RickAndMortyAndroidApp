package com.daveloper.rickandmortyapp.core.data.db.converters

import androidx.room.TypeConverter
import com.daveloper.rickandmortyapp.core.data.db.model.LocationBasicEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationBasicEntityConverter {
    @TypeConverter
    fun fromLocationBasicEntity(
        value: LocationBasicEntity
    ): String {
        val gson = Gson()
        val type = object : TypeToken<LocationBasicEntity>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLocationBasicEntity(
        value: String
    ): LocationBasicEntity {
        val gson = Gson()
        val type = object : TypeToken<LocationBasicEntity>() {}.type
        return gson.fromJson(value, type)
    }
}