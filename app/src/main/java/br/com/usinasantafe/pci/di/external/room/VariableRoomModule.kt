package br.com.usinasantafe.pci.di.external.room

import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.pci.external.room.dao.variable.RespDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRoomModule {

    @Provides
    @Singleton
    fun provideHeaderDao(database: DatabaseRoom): HeaderDao {
        return database.headerDao()
    }

    @Provides
    @Singleton
    fun provideRespRoom(database: DatabaseRoom): RespDao {
        return database.respDao()
    }


}