package br.com.usinasantafe.pci.di.external.room

import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.ColabDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRoomModule {

    @Provides
    @Singleton
    fun provideColabDao(database: DatabaseRoom): ColabDao {
        return database.colabDao()
    }

}