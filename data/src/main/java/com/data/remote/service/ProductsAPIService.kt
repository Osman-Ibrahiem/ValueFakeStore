package com.data.remote.service

import com.data.remote.model.reponses.ProductRemote
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

interface ProductsAPIService {

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<ProductsAPIService>()
    }

    @GET("products")
    suspend fun getAllProducts(): Response<ArrayList<ProductRemote>>

}
