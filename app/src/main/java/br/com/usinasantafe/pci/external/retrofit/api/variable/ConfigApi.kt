package br.com.usinasantafe.pci.external.retrofit.api.variable

import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.pci.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.pci.utils.WEB_SAVE_TOKEN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConfigApi {

    @POST(WEB_SAVE_TOKEN)
    suspend fun send(@Body config: ConfigRetrofitModelOutput): Response<ConfigRetrofitModelInput>

}