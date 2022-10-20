package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Restaurants(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("neighborhood") var neighborhood: String? = null,
    @SerializedName("photograph") var photograph: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("latlng") var latlng: Latlng? = Latlng(),
    @SerializedName("cuisine_type") var cuisineType: String? = null,
    @SerializedName("operating_hours") var operatingHours: OperatingHours? = OperatingHours(),
    @SerializedName("reviews") var reviews: ArrayList<Reviews> = arrayListOf()

)