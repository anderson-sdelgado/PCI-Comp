package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface ListPlantNote {
    suspend operator fun invoke(): Result<List<PlantScreenModel>>
}

class IListPlantNote @Inject constructor(
    private val itemRepository: ItemRepository,
    private val plantRepository: PlantRepository
): ListPlantNote {

    override suspend fun invoke(): Result<List<PlantScreenModel>> {
        try {
            val result = itemRepository.listAll()
            if(result.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!
            val idPlantList = entityList.map { it.idPlantItem }.distinct()
            val resultPlantList = plantRepository.listByIds(
                ids = idPlantList
            )
            if(resultPlantList.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultPlantList.exceptionOrNull()!!
                )
            }
            val plantList = resultPlantList.getOrNull()!!
            val plantScreenModelList = plantList.map { it ->
                PlantScreenModel(
                    id = it.idPlant,
                    cod = it.codPlant,
                    desc = it.descPlant
                )
            }
            return Result.success(plantScreenModelList)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}