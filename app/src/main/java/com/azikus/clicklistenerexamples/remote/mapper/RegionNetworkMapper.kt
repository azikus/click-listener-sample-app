package com.azikus.clicklistenerexamples.remote.mapper

import com.azikus.clicklistenerexamples.domain.model.Region as RegionDomain
import com.azikus.clicklistenerexamples.remote.model.Region as RegionNetwork

class RegionNetworkMapper : NetworkMapper<RegionNetwork, RegionDomain> {
    override fun map(model: RegionNetwork): RegionDomain =
        RegionDomain(
            iso = model.iso,
            name = model.name
        )
}
