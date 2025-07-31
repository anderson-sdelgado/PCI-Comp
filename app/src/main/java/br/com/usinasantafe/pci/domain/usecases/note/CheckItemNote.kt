package br.com.usinasantafe.pci.domain.usecases.note

import javax.inject.Inject

interface CheckItemNote {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckItemNote @Inject constructor(
): CheckItemNote {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}