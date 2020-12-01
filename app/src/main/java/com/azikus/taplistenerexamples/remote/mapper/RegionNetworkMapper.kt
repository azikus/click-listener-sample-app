package com.azikus.taplistenerexamples.remote.mapper

import com.azikus.taplistenerexamples.domain.model.Region as RegionDomain
import com.azikus.taplistenerexamples.remote.model.Region as RegionNetwork

class RegionNetworkMapper : NetworkMapper<RegionNetwork, RegionDomain> {
    override fun map(model: RegionNetwork): RegionDomain =
        RegionDomain(
            iso = model.iso,
            name = model.name
        )
}
