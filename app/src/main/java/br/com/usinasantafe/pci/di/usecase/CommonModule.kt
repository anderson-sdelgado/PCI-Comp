package br.com.usinasantafe.pci.di.usecase

import br.com.usinasantafe.pci.domain.usecases.common.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    @Singleton
    fun bindGetToken(usecase: IGetToken): GetToken

}