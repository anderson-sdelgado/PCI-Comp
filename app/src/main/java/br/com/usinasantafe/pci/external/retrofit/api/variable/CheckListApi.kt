package br.com.usinasantafe.pci.external.retrofit.api.variable

import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.pci.utils.WEB_SAVE_CHECK_LIST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CheckListApi {

    @POST(WEB_SAVE_CHECK_LIST)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Response<List<HeaderRetrofitModelInput>>
}