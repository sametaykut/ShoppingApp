package com.samet.shoppapp.retrofit

import com.samet.shoppapp.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {


    @GET("products")
    suspend fun getProducts() : Response<ProductResponse>

}