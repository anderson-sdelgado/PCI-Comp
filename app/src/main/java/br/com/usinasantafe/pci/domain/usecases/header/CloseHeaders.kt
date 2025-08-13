package br.com.usinasantafe.pci.domain.usecases.header

import javax.inject.Inject

interface CloseHeaders {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseHeaders @Inject constructor(
): CloseHeaders {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}