package com.poc.myapplication.model

import com.google.gson.annotations.SerializedName


data class OperatingHours (

  @SerializedName("Monday"    ) var Monday    : String? = null,
  @SerializedName("Tuesday"   ) var Tuesday   : String? = null,
  @SerializedName("Wednesday" ) var Wednesday : String? = null,
  @SerializedName("Thursday"  ) var Thursday  : String? = null,
  @SerializedName("Friday"    ) var Friday    : String? = null,
  @SerializedName("Saturday"  ) var Saturday  : String? = null,
  @SerializedName("Sunday"    ) var Sunday    : String? = null

)