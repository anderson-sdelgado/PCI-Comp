package br.com.usinasantafe.pci.external.retrofit.api.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ItemRetrofitModel
import br.com.usinasantafe.pci.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.pci.utils.WEB_LIST_ITEM_BY_ID_OS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ItemApi {

    @POST(WEB_LIST_ITEM_BY_ID_OS)
    suspend fun listByIdOS(
        @Header("Authorization") auth: String,
        @Body idOS: Int
    ): Response<List<ItemRetrofitModel>>

}