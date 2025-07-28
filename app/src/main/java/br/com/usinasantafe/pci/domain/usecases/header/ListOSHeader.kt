package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.errors.resultFailureFinish
import br.com.usinasantafe.pci.domain.errors.resultFailureMiddle
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface ListOSHeader {
    suspend operator fun invoke(): Result<List<OSScreenModel>>
}

class IListOSHeader @Inject constructor(
    private val osRepository: OSRepository,
    private val plantRepository: PlantRepository
): ListOSHeader {

    override suspend fun invoke(): Result<List<OSScreenModel>> {
        try{
            val resultListOS = osRepository.listAll()
            if(resultListOS.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultListOS.exceptionOrNull()!!
                )
            }
            val osList = resultListOS.getOrNull()!!
            val resultListPlant = plantRepository.listAll()
            if(resultListPlant.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultListPlant.exceptionOrNull()!!
                )
            }
            val plantList = resultListPlant.getOrNull()!!
            val list = osList.map { os ->
                val plant = plantList.first { it.idPlant == os.idPlantOS }
                OSScreenModel(
                    id = os.idOS,
                    os = "OS ${os.nroOS}",
                    period = os.descPeriodOS,
                    codPlant = plant.codPlant,
                    descPlant = plant.descPlant
                )
            }
            return Result.success(list)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}