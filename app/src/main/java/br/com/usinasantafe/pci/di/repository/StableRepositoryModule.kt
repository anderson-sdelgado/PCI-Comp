package br.com.usinasantafe.pci.di.repository

import br.com.usinasantafe.pci.domain.repositories.stable.*
import br.com.usinasantafe.pci.infra.repositories.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRepositoryModule {

    @Binds
    @Singleton
    fun bindColabRepository(repository: IColabRepository): ColabRepository

    @Binds
    @Singleton
    fun bindOSRepository(repository: IOSRepository): OSRepository

}