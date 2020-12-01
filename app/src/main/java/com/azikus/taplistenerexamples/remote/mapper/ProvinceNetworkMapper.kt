package com.azikus.taplistenerexamples.remote.mapper

import com.azikus.taplistenerexamples.domain.model.Province as ProvinceDomain
import com.azikus.taplistenerexamples.remote.model.Province as ProvinceNetwork

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
