package com.azikus.clicklistenerexamples.remote

import com.azikus.clicklistenerexamples.domain.repository.CovidRepository
import com.azikus.clicklistenerexamples.remote.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Singleton
import com.azikus.clicklistenerexamples.domain.model.AdditionRegion as AdditionRegionDomain
import com.azikus.clicklistenerexamples.domain.model.City as CityDomain
import com.azikus.clicklistenerexamples.domain.model.Province as ProvinceDomain
import com.azikus.clicklistenerexamples.domain.model.Region as RegionDomain
import com.azikus.clicklistenerexamples.domain.model.ReportList as ReportListDomain
import com.azikus.clicklistenerexamples.remote.model.AdditionRegion as AdditionRegionNetwork
import com.azikus.clicklistenerexamples.remote.model.City as CityNetwork
import com.azikus.clicklistenerexamples.remote.model.Province as ProvinceNetwork
import com.azikus.clicklistenerexamples.remote.model.Region as RegionNetwork
import com.azikus.clicklistenerexamples.remote.model.ReportList as ReportListNetwork

@Module
@InstallIn(ApplicationComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideCovidRepository(
        httpClient: HttpClient
    ): CovidRepository {
        val cityMapper = CityNetworkMapper()
        val provinceMapper = ProvinceNetworkMapper()
        val regionMapper = RegionNetworkMapper()
        val additionRegionMapper = AdditionRegionNetworkMapper(cityMapper)
        val reportListMapper = ReportListNetworkMapper(additionRegionMapper)
        return CovidApiClient(httpClient, regionMapper, provinceMapper, reportListMapper)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.v(message)
                    }
                }
                level = LogLevel.INFO
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            defaultRequest {
                if (this.method != HttpMethod.Get) {
                    contentType(ContentType.Application.Json)
                }
                accept(ContentType.Application.Json)
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
}
