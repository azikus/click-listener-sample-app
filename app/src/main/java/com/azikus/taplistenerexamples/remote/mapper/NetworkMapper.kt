package com.azikus.taplistenerexamples.remote.mapper

interface NetworkMapper<in IN, out OUT> {
    fun map(model: IN): OUT
    fun mapAll(models: List<IN>): List<OUT> = models.map(::map)
}
