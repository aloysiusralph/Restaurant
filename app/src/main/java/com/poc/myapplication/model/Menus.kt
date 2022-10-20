package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Menus (

  @SerializedName("restaurantId" ) var restaurantId : Int?                  = null,
  @SerializedName("categories"   ) var categories   : ArrayList<Categories> = arrayListOf()

)