package com.azikus.taplistenerexamples.ui.corona

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import com.azikus.taplistenerexamples.R
import com.azikus.taplistenerexamples.domain.model.City
import com.azikus.taplistenerexamples.domain.model.Province
import com.azikus.taplistenerexamples.domain.model.Region
import com.azikus.taplistenerexamples.ui.shared.NoFilterAdapter
import com.azikus.taplistenerexamples.ui.shared.listener.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_corona.*
import kotlinx.android.synthetic.main.content_corona.*

@AndroidEntryPoint
class CoronaActivity : AppCompatActivity() {

    private val viewModel: CoronaViewModel by viewModels()

    private val asyncListeners: MutableList<OnTapListener> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona)
        setSupportActionBar(findViewById(R.id.toolbar))

        subscribeObservers()

        asyncListeners += asyncButton.onTapAsync {
            getStatistics()
        }
        asyncListeners += asyncButtonWithDisable.onTapAsyncAndDisable{
            getStatistics()
        }
        asyncListeners += asyncButtonWithColorChange.onTapAsyncAndChangeBackgroundColor(R.color.primaryColor, R.color.error) {
            getStatistics()
        }

        debouncedButton.onTapDebounced {
            getStatistics()
        }
        debouncedButtonWithDisable.onTapDebouncedAndDisable {
            getStatistics()
        }
        debouncedButtonWithColorChange.onTapDebouncedAndChangeBackgroundColor(enabledColor = R.color.primaryColor, disabledColor = R.color.error) {
            getStatistics()
        }

        viewModel.setStateEvent(CoronaStateEvent.GetCountries)
    }

    private fun getStatistics() {
        val dataState = viewModel.dataState.value
        val country = dataState?.countries?.firstOrNull { it.name == countrySelector.text.toString() }
        val province = dataState?.provinces?.firstOrNull { it.province == provinceSelector.text.toString() }
        val city = dataState?.cities?.firstOrNull { it.name == citySelector.text.toString() }

        val isoCountryCode = country?.iso
        if (isoCountryCode != null) {
            viewModel.setStateEvent(
                CoronaStateEvent.GetStatistics(
                    isoCountryCode = isoCountryCode,
                    provinceName = province?.province,
                    cityName = city?.name
                )
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            if (dataState.countries.isNotEmpty()) {
                displayCountries(dataState.countries)
            }
            if (dataState.provinces.isNotEmpty()) {
                displayProvinces(dataState.provinces)
            }
            if (dataState.cities.isNotEmpty()) {
                displayCities(dataState.cities)
            }
            displayLoadingIndicator(dataState is CoronaViewState.Loading)

            when (dataState) {
                is CoronaViewState.Uninitialized -> displayUninitialized()
                is CoronaViewState.Loaded -> displayStatistics(
                    dataState.lastUpdate,
                    dataState.confirmed,
                    dataState.deaths,
                    dataState.recovered,
                    dataState.confirmedDiff,
                    dataState.deathsDiff,
                    dataState.recoveredDiff,
                    dataState.active,
                    dataState.activeDiff,
                    dataState.fatalityRate
                )
                is CoronaViewState.NoData -> displayNoStatistics()
            }
        }
    }

    private fun displayCountries(countries: List<Region>) {
        countrySelector.isEnabled = true
        countrySelector.apply {
            setAdapter(NoFilterAdapter(this@CoronaActivity, countries.mapNotNull { it.name }.toTypedArray()))
            addTextChangedListener(afterTextChanged = { name ->
                val isoCountryCode = countries.firstOrNull { it.name == name.toString() }?.iso
                buttons.setChildrenEnabled(isoCountryCode != null)
                if (isoCountryCode != null) {
                    viewModel.setStateEvent(CoronaStateEvent.GetProvinces(isoCountryCode))
                }
            })
        }

        provinceSelector.setAdapter(null)
        provinceSelectorLayout.visibility = View.GONE

        citySelector.setAdapter(null)
        citySelectorLayout.visibility = View.GONE
    }

    private fun displayProvinces(provinces: List<Province>) {
        if (provinces.isNotEmpty()) {
            provinceSelectorLayout.visibility = View.VISIBLE
            provinceSelector.apply {
                setAdapter(NoFilterAdapter(this@CoronaActivity, provinces.map { it.province }.toTypedArray()))
                addTextChangedListener(afterTextChanged = { text ->
                    val province = provinces.firstOrNull { it.province == text.toString() }
                    if (province != null) {
                        viewModel.setStateEvent(CoronaStateEvent.GetCities(province.iso, province.province))
                    }
                })
            }

            citySelector.setAdapter(null)
            citySelectorLayout.visibility = View.GONE
        } else {
            provinceSelector.setAdapter(null)
            provinceSelectorLayout.visibility = View.GONE
        }
    }

    private fun displayCities(cities: List<City>) {
        if (cities.isNotEmpty()) {
            citySelectorLayout.visibility = View.VISIBLE
            citySelector.setAdapter(NoFilterAdapter(this, cities.map { it.name }.toTypedArray()))
        } else {
            citySelector.setAdapter(null)
            citySelectorLayout.visibility = View.GONE
        }
    }

    private fun displayStatistics(
        lastUpdate: String?,
        confirmed: Long?,
        deaths: Long?,
        recovered: Long?,
        confirmedDiff: Long?,
        deathsDiff: Long?,
        recoveredDiff: Long?,
        active: Long?,
        activeDiff: Long?,
        fatalityRate: Double?
    ) {
        confirmedCasesValue.text = getString(R.string.corona_screen_confirmed_cases_value, confirmed, formatDiff(confirmedDiff))
        activeCasesValue.text = getString(R.string.corona_screen_active_cases_value, active, formatDiff(activeDiff))
        deathsValue.text = getString(R.string.corona_screen_deaths_value, deaths, formatDiff(deathsDiff))
        recoveredValue.text = getString(R.string.corona_screen_recovered_value, recovered, formatDiff(recoveredDiff))

        asyncListeners.forEach { it.isEnabled = true }
        content.showOnly(R.id.statistics)
    }

    private fun displayUninitialized() {
        content.showOnly(R.id.uninitialized)
    }

    private fun displayNoStatistics() {
        content.showOnly(R.id.noStatisticsAvailable)
    }

    private fun displayLoadingIndicator(isDisplayed: Boolean) {
        content.showOnly(R.id.loading)
    }

    private fun formatDiff(number: Long?): String =
        when {
            number == null -> "0"
            number > 0 -> "+$number"
            else -> "$number"
        }

    private fun ViewGroup.showOnly(@IdRes viewId: Int) {
        for (child in children) {
            child.visibility = if (child.id == viewId) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun ViewGroup.setChildrenEnabled(enable: Boolean) {
        for (child in children) {
            child.isEnabled = enable
            if (child is ViewGroup) {
                child.setChildrenEnabled(enable)
            }
        }
    }

    companion object {
        fun startWith(context: Context) {
            context.startActivity(createIntent(context))
        }

        fun createIntent(context: Context): Intent =
            Intent(context, CoronaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
    }
}
