package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Reviews(

    @SerializedName("name") var name: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("rating") var rating: Int? = null,
    @SerializedName("comments") var comments: String? = null

)