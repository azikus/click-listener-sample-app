package com.azikus.clicklistenerexamples.domain.repository

import com.azikus.clicklistenerexamples.domain.model.Province
import com.azikus.clicklistenerexamples.domain.model.Region
import com.azikus.clicklistenerexamples.domain.model.ReportList

interface CovidRepository {

    suspend fun getRegions(): List<Region>

    suspend fun getProvinces(isoCountryCode: String): List<Province>

    suspend fun getReportList(isoCountryCode: String, provinceName: String?, cityName: String?): List<ReportList>
}
