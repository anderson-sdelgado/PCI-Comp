package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import javax.inject.Inject

interface GetItem {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetItem @Inject constructor(
    private val itemRepository: ItemRepository,
): GetItem {

    override suspend fun invoke(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

}