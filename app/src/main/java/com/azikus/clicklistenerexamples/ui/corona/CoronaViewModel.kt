package com.azikus.clicklistenerexamples.ui.corona

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.azikus.clicklistenerexamples.domain.repository.CovidRepository
import kotlinx.coroutines.launch

class CoronaViewModel @ViewModelInject constructor(
    private val repository: CovidRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val dataState: LiveData<CoronaViewState>
        get() = _dataState
    private val _dataState: MutableLiveData<CoronaViewState> = MutableLiveData()

    fun setStateEvent(stateEvent: CoronaStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                CoronaStateEvent.GetCountries -> {
                    val countries = repository.getRegions()
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        when (lastState) {
                            is CoronaViewState.Uninitialized -> lastState.copy(countries = countries)
                            is CoronaViewState.Loading -> lastState.copy(countries = countries)
                            is CoronaViewState.Loaded -> lastState.copy(countries = countries)
                            is CoronaViewState.NoData -> lastState.copy(countries = countries)
                        }
                    )
                }
                is CoronaStateEvent.GetProvinces -> {
                    val provinces = repository.getProvinces(stateEvent.isoCountryCode)
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        when (lastState) {
                            is CoronaViewState.Uninitialized -> lastState.copy(provinces = provinces)
                            is CoronaViewState.Loading -> lastState.copy(provinces = provinces)
                            is CoronaViewState.Loaded -> lastState.copy(provinces = provinces)
                            is CoronaViewState.NoData -> lastState.copy(provinces = provinces)
                        }
                    )
                }
                is CoronaStateEvent.GetCities -> {
                    val report = repository.getReportList(stateEvent.isoCountryCode, stateEvent.provinceName, null).getOrNull(0)
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        when (lastState) {
                            is CoronaViewState.Uninitialized -> lastState.copy(cities = report?.region?.cities.orEmpty())
                            is CoronaViewState.Loading -> lastState.copy(cities = report?.region?.cities.orEmpty())
                            is CoronaViewState.Loaded -> lastState.copy(cities = report?.region?.cities.orEmpty())
                            is CoronaViewState.NoData -> lastState.copy(cities = report?.region?.cities.orEmpty())
                        }
                    )
                }
                is CoronaStateEvent.GetStatistics -> {
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        CoronaViewState.Loading(
                            countries = lastState.countries,
                            provinces = lastState.provinces,
                            cities = lastState.cities,
                            selectedCountry = lastState.selectedCountry,
                            selectedProvince = lastState.selectedProvince,
                            selectedCity = lastState.selectedCity
                        )
                    )
                    val report = repository.getReportList(stateEvent.isoCountryCode, stateEvent.provinceName, stateEvent.cityName).getOrNull(0)
                    if (report == null) {
                        _dataState.postValue(
                            CoronaViewState.NoData(
                                countries = lastState.countries,
                                provinces = lastState.provinces,
                                cities = lastState.cities,
                                selectedCountry = lastState.selectedCountry,
                                selectedProvince = lastState.selectedProvince,
                                selectedCity = lastState.selectedCity
                            )
                        )
                    } else {
                        _dataState.postValue(
                            CoronaViewState.Loaded(
                                countries = lastState.countries,
                                provinces = lastState.provinces,
                                cities = lastState.cities,
                                selectedCountry = lastState.selectedCountry,
                                selectedProvince = lastState.selectedProvince,
                                selectedCity = lastState.selectedCity,
                                lastUpdate = report.lastUpdate,
                                confirmed = report.confirmed,
                                deaths = report.deaths,
                                recovered = report.recovered,
                                confirmedDiff = report.confirmedDiff,
                                deathsDiff = report.deathsDiff,
                                recoveredDiff = report.recoveredDiff,
                                active = report.active,
                                activeDiff = report.activeDiff,
                                fatalityRate = report.fatalityRate
                            )
                        )
                    }
                }
            }
        }
    }
}

sealed class CoronaStateEvent {
    object GetCountries : CoronaStateEvent()

    class GetProvinces(
        val isoCountryCode: String
    ) : CoronaStateEvent()

    class GetCities(
        val isoCountryCode: String,
        val provinceName: String
    ) : CoronaStateEvent()

    class GetStatistics(
        val isoCountryCode: String,
        val provinceName: String?,
        val cityName: String?
    ) : CoronaStateEvent()
}
