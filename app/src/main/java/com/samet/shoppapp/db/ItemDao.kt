package com.samet.shoppapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samet.shoppapp.models.ProductItem

@Dao
interface ItemDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(productItem: ProductItem):Long

    @Query("SELECT * FROM mybox")
    fun getAllItems(): LiveData<List<ProductItem>>

    @Query("DELETE  FROM  mybox")
    suspend fun deleteAllItems()

    @Delete
    suspend fun deleteItems(productItem: ProductItem)


}