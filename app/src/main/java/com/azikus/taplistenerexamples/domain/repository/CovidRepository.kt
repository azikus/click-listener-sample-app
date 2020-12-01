package com.azikus.taplistenerexamples.domain.repository

import com.azikus.taplistenerexamples.domain.model.Province
import com.azikus.taplistenerexamples.domain.model.Region
import com.azikus.taplistenerexamples.domain.model.ReportList

interface CovidRepository {

    suspend fun getRegions(): List<Region>

    suspend fun getProvinces(isoCountryCode: String): List<Province>

    suspend fun getReportList(isoCountryCode: String, provinceName: String?, cityName: String?): List<ReportList>
}
