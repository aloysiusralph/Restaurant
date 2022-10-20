package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class Latlng (

  @SerializedName("lat" ) var lat : Double? = null,
  @SerializedName("lng" ) var lng : Double? = null

)