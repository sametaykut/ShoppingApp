package com.samet.shoppapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite

@Dao
interface ItemFavouriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(productItemFavourite: ProductItemFavourite):Long

    @Query("SELECT * FROM favourite")
    fun getAllItems(): LiveData<List<ProductItem>>

    @Delete
    suspend fun deleteItems(productItemFavourite: ProductItemFavourite)

}