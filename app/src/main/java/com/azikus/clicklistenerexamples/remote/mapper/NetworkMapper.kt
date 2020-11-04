package com.azikus.clicklistenerexamples.remote.mapper

interface NetworkMapper<in IN, out OUT> {
    fun map(model: IN): OUT
    fun mapAll(models: List<IN>): List<OUT> = models.map(::map)
}
