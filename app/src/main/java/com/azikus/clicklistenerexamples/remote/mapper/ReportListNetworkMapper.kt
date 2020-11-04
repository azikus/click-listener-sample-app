package com.azikus.clicklistenerexamples.remote.mapper

import com.azikus.clicklistenerexamples.domain.model.AdditionRegion as AdditionRegionDomain
import com.azikus.clicklistenerexamples.domain.model.ReportList as ReportListDomain
import com.azikus.clicklistenerexamples.remote.model.AdditionRegion as AdditionRegionNetwork
import com.azikus.clicklistenerexamples.remote.model.ReportList as ReportListNetwork

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
