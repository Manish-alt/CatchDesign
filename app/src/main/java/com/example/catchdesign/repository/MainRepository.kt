package com.example.catchdesign.repository

import com.example.catchdesign.model.ResponseModel
import com.example.catchdesign.di.service.ApiService
import io.ktor.client.statement.HttpResponse

class MainRepository(private val apiService: ApiService) {
    suspend fun fetchUsers(): List<ResponseModel> = apiService.getUsers()
    suspend fun addUser(user: ResponseModel): ResponseModel = apiService.addUser(user)
    suspend fun updateUser(user: ResponseModel): ResponseModel = apiService.updateUser(user)
    suspend fun deleteUser(id: Int?): HttpResponse = apiService.deleteUser(id)
}