package com.azikus.clicklistenerexamples.remote.mapper

import com.azikus.clicklistenerexamples.domain.model.AdditionRegion as AdditionRegionDomain
import com.azikus.clicklistenerexamples.domain.model.City as CityDomain
import com.azikus.clicklistenerexamples.remote.model.AdditionRegion as AdditionRegionNetwork
import com.azikus.clicklistenerexamples.remote.model.City as CityNetwork

class AdditionRegionNetworkMapper(
    private val cityMapper: NetworkMapper<CityNetwork, CityDomain>
) : NetworkMapper<AdditionRegionNetwork, AdditionRegionDomain> {
    override fun map(model: AdditionRegionNetwork): AdditionRegionDomain =
        AdditionRegionDomain(
            iso = model.iso,
            name = model.name,
            province = model.province,
            latitude = model.latitude.orEmpty(),
            longitude = model.longitude.orEmpty(),
            cities = cityMapper.mapAll(model.cities)
        )
}
