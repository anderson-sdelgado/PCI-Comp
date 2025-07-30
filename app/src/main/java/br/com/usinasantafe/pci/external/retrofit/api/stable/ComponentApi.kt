package br.com.usinasantafe.pci.external.retrofit.api.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ComponentRetrofitModel
import br.com.usinasantafe.pci.utils.WEB_ALL_COMPONENT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ComponentApi {

    @GET(WEB_ALL_COMPONENT)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ComponentRetrofitModel>>

}