package br.com.usinasantafe.pci.di.usecase

import br.com.usinasantafe.pci.domain.usecases.update.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpdateModule {

    @Binds
    @Singleton
    fun bindUpdateTableColab(usecase: IUpdateTableColabReg): UpdateTableColabReg

    @Binds
    @Singleton
    fun bindUpdateTableComponent(usecase: IUpdateTableComponent): UpdateTableComponent

    @Binds
    @Singleton
    fun bindUpdateTableItemByIdOS(usecase: IUpdateTableItemByIdOS): UpdateTableItemByIdOS

    @Binds
    @Singleton
    fun bindUpdateTableOSByIdFactorySection(usecase: IUpdateTableOSByIdFactorySection): UpdateTableOSByIdFactorySection

    @Binds
    @Singleton
    fun bindUpdateTablePlantByIdFactorySection(usecase: IUpdateTablePlantByIdFactorySection): UpdateTablePlantByIdFactorySection

    @Binds
    @Singleton
    fun bindUpdateTableService(usecase: IUpdateTableService): UpdateTableService

}