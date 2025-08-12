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

    @Binds
    @Singleton
    fun bindGetItem(usecase: IGetDescItem): GetDescItem

    @Binds
    @Singleton
    fun bindSetRespItem(usecase: ISetRespItem): SetRespItem

    @Binds
    @Singleton
    fun bindGetResp(usecase: IGetResp): GetResp

    @Binds
    @Singleton
    fun bindCloseItemNote(usecase: ICloseItemsNote): CloseItemsNote
    
    @Binds
    @Singleton
    fun bindCheckItemsOpen(usecase: ICheckItemsOpen): CheckItemsOpen
}