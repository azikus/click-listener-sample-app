package com.azikus.taplistenerexamples.remote.mapper

import com.azikus.taplistenerexamples.domain.model.AdditionRegion as AdditionRegionDomain
import com.azikus.taplistenerexamples.domain.model.ReportList as ReportListDomain
import com.azikus.taplistenerexamples.remote.model.AdditionRegion as AdditionRegionNetwork
import com.azikus.taplistenerexamples.remote.model.ReportList as ReportListNetwork

class ReportListNetworkMapper(
    private val regionMapper: NetworkMapper<AdditionRegionNetwork, AdditionRegionDomain>
) : NetworkMapper<ReportListNetwork, ReportListDomain> {
    override fun map(model: ReportListNetwork): ReportListDomain =
        ReportListDomain(
            date = model.date,
            confirmed = model.confirmed,
            deaths = model.deaths,
            recovered = model.recovered,
            confirmedDiff = model.confirmedDiff,
            deathsDiff = model.deathsDiff,
            recoveredDiff = model.recoveredDiff,
            lastUpdate = model.lastUpdate,
            active = model.active,
            activeDiff = model.activeDiff,
            fatalityRate = model.fatalityRate,
            region = regionMapper.map(model.region)
        )
}
