package com.samet.shoppapp.repository

import com.samet.shoppapp.db.ItemDatabase
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite
import com.samet.shoppapp.retrofit.RetrofitInstance

class ProductRepository(val db: ItemDatabase) {

    suspend fun getProduct() = RetrofitInstance.api.getProducts()

    suspend fun upsert(productItem: ProductItem) = db.getItemDao().upsert(productItem)

    fun getAllItems() = db.getItemDao().getAllItems()

    suspend fun deleteItems(productItem: ProductItem) = db.getItemDao().deleteItems(productItem)

    suspend fun favouriteUpsert(productItemFavourite: ProductItemFavourite) = db.getItemFavouriteDao().upsert(productItemFavourite)

    fun getAllFavouriteItems() = db.getItemFavouriteDao().getAllItems()

    suspend fun deleteFavouriteItems(productItemFavourite: ProductItemFavourite) = db.getItemFavouriteDao().deleteItems(productItemFavourite)

    suspend fun deleteAllItems() = db.getItemDao().deleteAllItems()
}