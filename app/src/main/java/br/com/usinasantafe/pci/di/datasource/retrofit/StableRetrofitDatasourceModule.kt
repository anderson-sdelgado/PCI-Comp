package br.com.usinasantafe.pci.di.datasource.retrofit

import br.com.usinasantafe.pci.external.retrofit.datasource.stable.*
import br.com.usinasantafe.pci.infra.datasource.retrofit.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindColabRetrofitDatasource(dataSource: IColabRetrofitDatasource): ColabRetrofitDatasource

}