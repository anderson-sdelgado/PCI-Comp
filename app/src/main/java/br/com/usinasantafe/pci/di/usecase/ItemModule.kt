package br.com.usinasantafe.pci.di.usecase

import br.com.usinasantafe.pci.domain.usecases.note.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ItemModule {

    @Binds
    @Singleton
    fun bindCheckItemNote(usecase: ICheckItemNote): CheckItemNote

    @Binds
    @Singleton
    fun bindListPlantNote(usecase: IListPlantNote): ListPlantNote

    @Binds
    @Singleton
    fun bindListItemNote(usecase: IListItemNote): ListItemNote

}