package com.example.ecommerceshop.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String

) : Serializable