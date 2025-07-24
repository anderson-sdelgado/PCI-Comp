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
}