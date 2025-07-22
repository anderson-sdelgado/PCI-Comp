package br.com.usinasantafe.pci.di.datasource.room

import br.com.usinasantafe.pci.external.room.datasource.stable.*
import br.com.usinasantafe.pci.infra.datasource.room.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindColabRoomDatasource(dataSource: IColabRoomDatasource): ColabRoomDatasource

}