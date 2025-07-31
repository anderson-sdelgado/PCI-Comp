package br.com.usinasantafe.pci.di.external.room

import br.com.usinasantafe.pci.external.room.DatabaseRoom
import br.com.usinasantafe.pci.external.room.dao.stable.*
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

    @Provides
    @Singleton
    fun provideComponentDao(database: DatabaseRoom): ComponentDao {
        return database.componentDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: DatabaseRoom): ItemDao {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun provideOSDao(database: DatabaseRoom): OSDao {
        return database.osDao()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: DatabaseRoom): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun provideServiceDao(database: DatabaseRoom): ServiceDao {
        return database.serviceDao()
    }

}