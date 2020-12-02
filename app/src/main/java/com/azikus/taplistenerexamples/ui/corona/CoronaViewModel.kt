package com.azikus.taplistenerexamples.ui.corona

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azikus.taplistenerexamples.domain.repository.CovidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CoronaViewModel @ViewModelInject constructor(
    private val repository: CovidRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val dataState: LiveData<CoronaViewState>
        get() = _dataState
    private val _dataState: MutableLiveData<CoronaViewState> = MutableLiveData()

    fun setStateEvent(stateEvent: CoronaStateEvent) {
        viewModelScope.launch(context = Dispatchers.Default) {
            when (stateEvent) {
                CoronaStateEvent.GetCountries -> {
                    val countries = repository.getRegions()
                    val lastState = _dataState.value.orInitialState()

                    try {
                        _dataState.postValue(
                            lastState.copyHelper(
                                countries = countries,
                                provinces = emptyList(),
                                cities = emptyList()
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is CoronaStateEvent.GetProvinces -> {
                    val provinces = repository.getProvinces(stateEvent.isoCountryCode)
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        lastState.copyHelper(
                            provinces = provinces,
                            cities = emptyList()
                        )
                    )
                }
                is CoronaStateEvent.GetCities -> {
                    val report = repository.getReportList(stateEvent.isoCountryCode, stateEvent.provinceName, null).getOrNull(0)
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        lastState.copyHelper(cities = report?.region?.cities.orEmpty())
                    )
                }
                is CoronaStateEvent.GetStatistics -> {
                    val lastState = _dataState.value.orInitialState()
                    _dataState.postValue(
                        CoronaViewState.Loading(
                            countries = lastState.countries,
                            provinces = lastState.provinces,
                            cities = lastState.cities
                        )
                    )
                    val report = repository.getReportList(stateEvent.isoCountryCode, stateEvent.provinceName, stateEvent.cityName).getOrNull(0)
                    if (report == null) {
                        _dataState.postValue(
                            CoronaViewState.NoData(
                                countries = lastState.countries,
                                provinces = lastState.provinces,
                                cities = lastState.cities
                            )
                        )
                    } else {
                        _dataState.postValue(
                            CoronaViewState.Loaded(
                                countries = lastState.countries,
                                provinces = lastState.provinces,
                                cities = lastState.cities,
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
