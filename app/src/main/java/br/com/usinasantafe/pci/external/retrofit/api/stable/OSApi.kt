package br.com.usinasantafe.pci.external.retrofit.api.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.pci.utils.WEB_LIST_OS_BY_ID_FACTORY_SECTION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OSApi {

    @POST(WEB_LIST_OS_BY_ID_FACTORY_SECTION)
    suspend fun listByIdFactorySection(
        @Header("Authorization") auth: String,
        @Body idFactorySection: Int
    ): Response<List<OSRetrofitModel>>

}