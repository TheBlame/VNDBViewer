package com.example.vndbviewer.data.database

import android.util.Log
import androidx.room.TypeConverter
import com.example.vndbviewer.data.network.pojo.Tags
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RoomTypeConverters {

    @TypeConverter
    fun convertTagsListToJsonString(tags: List<Tags>?): String? {
        return Gson().toJson(tags) ?: null
    }

    @TypeConverter
    fun convertFromJsonToTagsList(json: String): List<Tags> {
        return try {
            val listType: Type = object : TypeToken<ArrayList<Tags?>?>() {}.type
            Gson().fromJson(json, listType)
        } catch (e: Exception) {
            listOf()
        }
    }
}