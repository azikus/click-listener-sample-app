package com.azikus.clicklistenerexamples.remote.mapper

import com.azikus.clicklistenerexamples.domain.model. City as  CityDomain
import com.azikus.clicklistenerexamples.remote.model. City as  CityNetwork

class CityNetworkMapper : NetworkMapper< CityNetwork,  CityDomain> {
    override fun map(model:  CityNetwork):  CityDomain =
         CityDomain(
            name = model.name,
            date = model.date,
            fips = model.fips,
            latitude = model.latitude,
            longitude = model.longitude,
            confirmed = model.confirmed,
            deaths = model.deaths,
            confirmedDiff = model.confirmedDiff,
            deathsDiff = model.deathsDiff,
            lastUpdate = model.lastUpdate
        )
}
