package br.com.usinasantafe.pci.di.usecase

import br.com.usinasantafe.pci.domain.usecases.header.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HeaderModule {

    @Binds
    @Singleton
    fun bindCheckAndSetRegColab(usecase: ICheckAndSetRegColabHeader): CheckAndSetRegColabHeader

    @Binds
    @Singleton
    fun bindListOS(usecase: IListOSHeader): ListOSHeader

    @Binds
    @Singleton
    fun bindSetIdOS(usecase: ISetIdOSHeader): SetIdOSHeader

}