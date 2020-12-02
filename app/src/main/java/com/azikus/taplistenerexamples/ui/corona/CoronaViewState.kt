package com.azikus.taplistenerexamples.ui.corona

import com.azikus.taplistenerexamples.domain.model.City
import com.azikus.taplistenerexamples.domain.model.Province
import com.azikus.taplistenerexamples.domain.model.Region

sealed class CoronaViewState {
    abstract val countries: List<Region>
    abstract val provinces: List<Province>
    abstract val cities: List<City>

    fun copyHelper(
        countries: List<Region> = this.countries,
        provinces: List<Province> = this.provinces,
        cities: List<City> = this.cities
    ): CoronaViewState =
        when (this) {
            is Uninitialized -> copy(countries, provinces, cities)
            is Loading -> copy(countries, provinces, cities)
            is Loaded -> copy(countries, provinces, cities)
            is NoData -> copy(countries, provinces, cities)
        }

    data class Uninitialized(
        override val countries: List<Region> = emptyList(),
        override val provinces: List<Province> = emptyList(),
        override val cities: List<City> = emptyList()
    ) : CoronaViewState()

    data class Loading(
        override val countries: List<Region>,
        override val provinces: List<Province>,
        override val cities: List<City>
    ) : CoronaViewState()

    data class Loaded(
        override val countries: List<Region>,
        override val provinces: List<Province>,
        override val cities: List<City>,
        val lastUpdate: String? = null,
        val confirmed: Long? = null,
        val deaths: Long? = null,
        val recovered: Long? = null,
        val confirmedDiff: Long? = null,
        val deathsDiff: Long? = null,
        val recoveredDiff: Long? = null,
        val active: Long? = null,
        val activeDiff: Long? = null,
        val fatalityRate: Double? = null
    ) : CoronaViewState()

    data class NoData(
        override val countries: List<Region>,
        override val provinces: List<Province>,
        override val cities: List<City>
    ) : CoronaViewState()
}

fun CoronaViewState?.orInitialState() = this ?: CoronaViewState.Uninitialized()
