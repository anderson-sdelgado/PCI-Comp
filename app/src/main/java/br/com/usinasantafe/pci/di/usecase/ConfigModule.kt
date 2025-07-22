package br.com.usinasantafe.pci.di.usecase

import br.com.usinasantafe.pci.domain.usecases.config.CheckPassword
import br.com.usinasantafe.pci.domain.usecases.config.ICheckPassword
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConfigModule {

    @Binds
    @Singleton
    fun bindCheckPassword(usecase: ICheckPassword): CheckPassword

}