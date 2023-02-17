package com.example.albertsonscoding.model

import com.example.albertsonscoding.model.remote.AcronymService
import com.example.albertsonscoding.model.remote.dto.ResponseDTO
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AcronymRepo @Inject constructor(private val acronymService: AcronymService) {

    suspend fun getLongforms(shortform: String): List<String> {
        return acronymService.getLongforms(shortform).body()!!.mapResponse()
    }

    private suspend fun List<ResponseDTO>.mapResponse(): List<String> {
        return withContext(Dispatchers.Default) {
            map { response -> response.lfs.map { longform -> longform.lf } }.flatten()
        }
    }
}