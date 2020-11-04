package com.azikus.clicklistenerexamples.domain.model

data class AdditionRegion(
     val iso: String,
     val name: String,
     val province: String,
     val latitude: String,
     val longitude: String,
     val cities: List<City>
)
