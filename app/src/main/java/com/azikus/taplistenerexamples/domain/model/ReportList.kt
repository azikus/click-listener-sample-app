package com.azikus.taplistenerexamples.domain.model

data class ReportList(
     val date: String,
     val confirmed: Long,
     val deaths: Long,
     val recovered: Long,
     val confirmedDiff: Long,
     val deathsDiff: Long,
     val recoveredDiff: Long,
     val lastUpdate: String,
     val active: Long,
     val activeDiff: Long,
     val fatalityRate: Double,
     val region: AdditionRegion
)
