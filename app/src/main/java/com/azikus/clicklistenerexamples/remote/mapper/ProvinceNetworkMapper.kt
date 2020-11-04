package com.azikus.clicklistenerexamples.remote.mapper

import com.azikus.clicklistenerexamples.domain.model.Province as ProvinceDomain
import com.azikus.clicklistenerexamples.remote.model.Province as ProvinceNetwork

class ProvinceNetworkMapper : NetworkMapper<ProvinceNetwork, ProvinceDomain> {
    override fun map(model: ProvinceNetwork): ProvinceDomain =
        ProvinceDomain(
            iso = model.iso,
            name = model.name,
            province = model.province,
            latitude = model.latitude.orEmpty(),
            longitude = model.longitude.orEmpty()
        )
}
