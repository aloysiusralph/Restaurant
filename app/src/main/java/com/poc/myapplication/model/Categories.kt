package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Categories(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("menu-items") var menuItems: ArrayList<MenuItems> = arrayListOf()

)