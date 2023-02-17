package com.example.albertsonscoding.model.remote

import com.example.albertsonscoding.model.remote.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymService {

    @GET(".")
    suspend fun getLongforms(@Query(SF_QUERY) shortform: String): Response<List<ResponseDTO>>

    companion object {
        private const val SF_QUERY = "sf"
    }
}
