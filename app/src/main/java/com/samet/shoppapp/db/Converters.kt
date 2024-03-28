package com.samet.shoppapp.db

import androidx.room.TypeConverter
import com.samet.shoppapp.models.Rating

class Converters {

    @TypeConverter
    fun fromRating(rating: Rating):Double {
        return rating.rate
    }

    @TypeConverter
    fun toRating(rating: Double): Rating {
        return Rating(0, rating)
    }
}