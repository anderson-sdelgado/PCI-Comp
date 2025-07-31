package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.presenter.model.ItemScreenModel
import javax.inject.Inject

interface ListItemNote {
    suspend operator fun invoke(idPlant: Int): Result<List<ItemScreenModel>>
}

class IListItemNote @Inject constructor(
): ListItemNote {

    override suspend fun invoke(idPlant: Int): Result<List<ItemScreenModel>> {
        TODO("Not yet implemented")
    }

}