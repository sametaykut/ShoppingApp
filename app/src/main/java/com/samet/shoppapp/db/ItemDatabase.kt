package com.samet.shoppapp.db

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite

@Database(
    entities =[ProductItem::class,ProductItemFavourite::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class ItemDatabase : RoomDatabase(){
    abstract fun getItemDao():ItemDao
    abstract fun getItemFavouriteDao():ItemFavouriteDAO
    companion object{
        @Volatile
        private var instance: ItemDatabase? = null
        private val LOCK =  Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java,
                "item_db.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}