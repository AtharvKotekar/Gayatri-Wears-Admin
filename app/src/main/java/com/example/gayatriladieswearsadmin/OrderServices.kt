package com.example.gayatriladieswearsadmin

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL = "https://apiv2.shiprocket.in"

interface OrderServices {

    @POST("/v1/external/auth/login")
    fun getToken(
        @Body
        token: Token
    ):Call<TokenCall>

    @GET("/v1/external/orders/show/{id}")
    fun getOrderDetail(
        @HeaderMap headerMap: Map<String, String>,
        @Path("id") orderId: String
    ): Call<JsonObject>



}