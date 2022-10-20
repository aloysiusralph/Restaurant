package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Restaurant (

  @SerializedName("restaurants" ) var restaurants : List<Restaurants> = arrayListOf()

)