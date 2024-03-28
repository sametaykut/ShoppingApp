package com.samet.shoppapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samet.shoppapp.repository.ProductRepository

class ProductViewModelProvider (val app:Application, val productRepository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(app,productRepository) as T
    }
}