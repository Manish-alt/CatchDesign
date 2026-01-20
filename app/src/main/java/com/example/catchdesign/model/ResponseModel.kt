package com.example.catchdesign.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel (
    var id: Int?,
    var title: String?,
    var subtitle: String?,
    var content: String?
)
