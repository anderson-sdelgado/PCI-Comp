package br.com.usinasantafe.pci.di.external.retrofit

import br.com.usinasantafe.pci.di.provider.DefaultRetrofit
import br.com.usinasantafe.pci.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pci.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.pci.external.retrofit.api.stable.PlantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRetrofitModule {

    @Provides
    @Singleton
    fun colabApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ColabApi = retrofit.create(ColabApi::class.java)

    @Provides
    @Singleton
    fun osApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): OSApi = retrofit.create(OSApi::class.java)

    @Provides
    @Singleton
    fun plantApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): PlantApi = retrofit.create(PlantApi::class.java)

}