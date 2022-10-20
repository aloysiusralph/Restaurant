package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class MenuItems (

  @SerializedName("id"          ) var id          : String?           = null,
  @SerializedName("name"        ) var name        : String?           = null,
  @SerializedName("description" ) var description : String?           = null,
  @SerializedName("price"       ) var price       : String?           = null,
  @SerializedName("images"      ) var images      : ArrayList<String> = arrayListOf()

)