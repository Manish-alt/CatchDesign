package com.example.catchdesign.di.service

import com.example.catchdesign.model.ResponseModel
import com.example.catchdesign.utils.EndPoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class ApiService(private val client: HttpClient) {

    suspend fun getUsers(): List<ResponseModel> {
        val responseText = client.get(EndPoints.USERS).bodyAsText()
        val data = Json.decodeFromString<List<ResponseModel>>(responseText)
        return data
    }
    suspend fun addUser(user: ResponseModel): ResponseModel = client.post(EndPoints.USERS) { setBody(user) }.body()
    suspend fun updateUser(user: ResponseModel): ResponseModel = client.put("${EndPoints.USERS}/${user.id}") { setBody(user) }.body()
    suspend fun deleteUser(id: Int?): HttpResponse = client.delete("${EndPoints.USERS}/$id")
}