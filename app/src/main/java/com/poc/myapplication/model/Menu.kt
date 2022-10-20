package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Menu (

  @SerializedName("menus" ) var menus : ArrayList<Menus> = arrayListOf()

)