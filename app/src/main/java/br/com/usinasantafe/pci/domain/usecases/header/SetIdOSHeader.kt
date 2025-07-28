package br.com.usinasantafe.pci.domain.usecases.header

import javax.inject.Inject

interface SetIdOSHeader {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ISetIdOSHeader @Inject constructor(
): SetIdOSHeader {

    override suspend fun invoke(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

}