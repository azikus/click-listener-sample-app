package com.azikus.clicklistenerexamples.remote

import com.azikus.clicklistenerexamples.domain.repository.CovidRepository
import com.azikus.clicklistenerexamples.remote.mapper.NetworkMapper
import com.azikus.clicklistenerexamples.remote.model.Province
import com.azikus.clicklistenerexamples.remote.model.Region
import com.azikus.clicklistenerexamples.remote.model.ReportList
import com.azikus.clicklistenerexamples.remote.model.Response
import io.ktor.client.*
import io.ktor.client.request.*
import com.azikus.clicklistenerexamples.domain.model.Province as ProvinceDomain
import com.azikus.clicklistenerexamples.domain.model.Region as RegionDomain
import com.azikus.clicklistenerexamples.domain.model.ReportList as ReportListDomain
import com.azikus.clicklistenerexamples.remote.model.Province as ProvinceNetwork
import com.azikus.clicklistenerexamples.remote.model.Region as RegionNetwork
import com.azikus.clicklistenerexamples.remote.model.ReportList as ReportListNetwork

class CovidApiClient(
    private val httpClient: HttpClient,
    private val regionMapper: NetworkMapper<RegionNetwork, RegionDomain>,
    private val provinceMapper: NetworkMapper<ProvinceNetwork, ProvinceDomain>,
    private val reportListMapper: NetworkMapper<ReportListNetwork, ReportListDomain>
) : CovidRepository {

    override suspend fun getRegions(): List<RegionDomain> {
        val response = httpClient.get< Response<List<Region>>>("$BASE_URL/regions")
        return regionMapper.mapAll(response.data)
    }

    override suspend fun getProvinces(isoCountryCode: String): List<ProvinceDomain> {
        val response = httpClient.get<Response<List<Province>>>("$BASE_URL/provinces/${isoCountryCode}")
        return provinceMapper.mapAll(response.data)
    }

    override suspend fun getReportList(
        isoCountryCode: String,
        provinceName: String?,
        cityName: String?
    ): List<ReportListDomain> {
        val response = httpClient.get<Response<List<ReportList>>>("$BASE_URL/reports") {
            parameter("iso", isoCountryCode)
            parameter("region_province", provinceName)
            parameter("city_name", cityName)
        }
        return reportListMapper.mapAll(response.data)
    }

    companion object {
        private const val BASE_URL = "https://covid-api.com/api"
    }
}
