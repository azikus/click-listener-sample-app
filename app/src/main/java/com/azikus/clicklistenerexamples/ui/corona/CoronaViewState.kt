package com.azikus.clicklistenerexamples.ui.corona

import com.azikus.clicklistenerexamples.domain.model.City
import com.azikus.clicklistenerexamples.domain.model.Province
import com.azikus.clicklistenerexamples.domain.model.Region

data class CoronaVsiewState(
    val countries: List<Region> = emptyList(),
    val provinces: List<Province> = emptyList(),
    val cities: List<City> = emptyList(),
    val selectedCountry: String? = null,
    val selectedProvince: String? = null,
    val selectedCity: String? = null,
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
)

sealed class CoronaViewState {
    abstract val countries: List<Region>
    abstract val provinces: List<Province>
    abstract val cities: List<City>
    abstract val selectedCountry: String?
    abstract val selectedProvince: String?
    abstract val selectedCity: String?

    fun <T> copy(
        countries: List<Region>? = null,
        provinces: List<Province>? = null,
        cities: List<City>? = null,
        selectedCountry: String? = null,
        selectedProvince: String? = null,
         selectedCity: String? = null
    ): T =
        when (this) {
            is Uninitialized -> copy(countries, provinces, cities, selectedCountry, selectedProvince, selectedCity)
            is Loading -> copy(countries, provinces, cities, selectedCountry, selectedProvince, selectedCity)
            is Loaded -> copy(countries, provinces, cities, selectedCountry, selectedProvince, selectedCity)
            is NoData -> copy(countries, provinces, cities, selectedCountry, selectedProvince, selectedCity)
        }

    data class Uninitialized(
        override val countries: List<Region> = emptyList(),
        override val provinces: List<Province> = emptyList(),
        override val cities: List<City> = emptyList(),
        override val selectedCountry: String? = null,
        override val selectedProvince: String? = null,
        override val selectedCity: String? = null
    ) : CoronaViewState()

    data class Loading(
        override val countries: List<Region>,
        override val provinces: List<Province>,
        override val cities: List<City>,
        override val selectedCountry: String? = null,
        override val selectedProvince: String? = null,
        override val selectedCity: String? = null
    ) : CoronaViewState()

    data class Loaded(
        override val countries: List<Region>,
        override val provinces: List<Province>,
        override val cities: List<City>,
        override val selectedCountry: String? = null,
        override val selectedProvince: String? = null,
        override val selectedCity: String? = null,
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
        override val cities: List<City>,
        override val selectedCountry: String? = null,
        override val selectedProvince: String? = null,
        override val selectedCity: String? = null
    ) : CoronaViewState()
}

fun CoronaViewState?.orInitialState() = this ?: CoronaViewState.Uninitialized()
