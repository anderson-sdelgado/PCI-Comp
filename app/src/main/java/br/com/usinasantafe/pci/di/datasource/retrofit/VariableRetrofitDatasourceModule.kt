package br.com.usinasantafe.pci.di.datasource.retrofit

import br.com.usinasantafe.pci.external.retrofit.datasource.variable.IConfigRetrofitDatasource
import br.com.usinasantafe.pci.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigRetrofitDatasource(dataSource: IConfigRetrofitDatasource): ConfigRetrofitDatasource

}