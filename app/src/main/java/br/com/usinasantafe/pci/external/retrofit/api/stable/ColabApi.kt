package br.com.usinasantafe.pci.external.retrofit.api.stable

import br.com.usinasantafe.pci.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.pci.utils.WEB_ALL_COLAB
import br.com.usinasantafe.pci.utils.WEB_GET_COLAB_BY_REG
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ColabApi {
    
    @GET(WEB_ALL_COLAB)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ColabRetrofitModel>>

    @POST(WEB_GET_COLAB_BY_REG)
    suspend fun getByReg(
        @Header("Authorization") auth: String,
        @Body regColab: Int
    ): Response<ColabRetrofitModel>

}