package com.samet.shoppapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samet.shoppapp.adapters.ShoppingItemsAdapter
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite
import com.samet.shoppapp.models.ProductResponse
import com.samet.shoppapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel (val app:Application, val productRepository: ProductRepository) : AndroidViewModel(app) {


        private val productList = MutableLiveData<ProductResponse>()
        private val loading = MutableLiveData<Boolean>()
        private val error = MutableLiveData<Boolean>()

        init {
                getProductList()
        }

        fun getProductList() {
                viewModelScope.launch {
                        loading.value=true
                        val response = productRepository.getProduct()
                        if (response.isSuccessful){
                                response.body()?.let {
                                        productList.value = response.body()
                                        loading.value=false
                                } ?: run {
                                        error.value=true
                                        loading.value=false
                                }
                        }else{
                                error.value=true
                                loading.value=false
                        }
                }
        }

        fun observeProductList() : MutableLiveData<ProductResponse>{
                return productList
        }
        fun observeLoading() : MutableLiveData<Boolean>{
                return loading
        }
        fun observeError() : MutableLiveData<Boolean>{
                return error
        }

        fun addToBox(productItem: ProductItem) = viewModelScope.launch {
                productRepository.upsert(productItem)
        }

        fun deleteToBox(productItem: ProductItem) = viewModelScope.launch {
                productRepository.deleteItems(productItem)
        }

        fun getAllItemsInBox() = productRepository.getAllItems()

        fun addToFavourite(productItemFavourite: ProductItemFavourite) = viewModelScope.launch {
                productRepository.favouriteUpsert(productItemFavourite)
        }

        fun deleteToFavourite(productItemFavourite: ProductItemFavourite) = viewModelScope.launch {
                productRepository.deleteFavouriteItems(productItemFavourite)
        }

        fun getAllIFavouritetems() = productRepository.getAllFavouriteItems()

        fun deleteAllItems() = viewModelScope.launch {
                productRepository.deleteAllItems()
        }
}