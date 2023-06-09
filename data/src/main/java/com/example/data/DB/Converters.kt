package com.example.data.DB

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromAuthorsList(value: List<String>): String {
        return value.joinToString {","}
    }

    @TypeConverter
    fun toAuthorsList(value: String) : List<String> {
        return value.split(",")
    }
}