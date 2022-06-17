package com.geektech.youtubeapp.core.network

import com.geektech.youtubeapp.core.network.result.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T>{
        try {
            val response = call()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null) return Resource.success(body)
            } else {
                return Resource.error(response.message(), response.body())
            }
        } catch (e: Exception){
            return Resource.error(e.message, null)
        }
        return Resource.error(null, null)
    }
}