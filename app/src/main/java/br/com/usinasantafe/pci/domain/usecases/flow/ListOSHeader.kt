package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.OSScreenModel
import br.com.usinasantafe.pci.utils.Status
import br.com.usinasantafe.pci.utils.getClassAndMethod
import javax.inject.Inject

interface ListOSHeader {
    suspend operator fun invoke(): Result<List<OSScreenModel>>
}

class IListOSHeader @Inject constructor(
    private val osRepository: OSRepository,
    private val plantRepository: PlantRepository,
    private val checkListRepository: CheckListRepository
): ListOSHeader {

    override suspend fun invoke(): Result<List<OSScreenModel>> {
        try{
            val resultGetIdFactorySection = checkListRepository.getIdFactorySectionHeaderOpen()
            if (resultGetIdFactorySection.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdFactorySection.exceptionOrNull()!!
                )
            }
            val idFactorySection = resultGetIdFactorySection.getOrNull()!!
            val resultListPlant = plantRepository.listByIdFactorySection(idFactorySection)
            if(resultListPlant.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListPlant.exceptionOrNull()!!
                )
            }
            val plantList = resultListPlant.getOrNull()!!
            val resultListOS = osRepository.listByIdFactorySection(idFactorySection)
            if(resultListOS.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListOS.exceptionOrNull()!!
                )
            }
            val osList = resultListOS.getOrNull()!!
            val idOSList = osList.map { it.idOS }.distinct()
            val resultListHeader = checkListRepository.listHeaderByIdOSList(idOSList)
            if(resultListHeader.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListHeader.exceptionOrNull()!!
                )
            }
            val headerList = resultListHeader.getOrNull()!!
            val resultIdColabHeader = checkListRepository.getIdColabHeaderOpen()
            if (resultIdColabHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultIdColabHeader.exceptionOrNull()!!
                )
            }
            val idColab = resultIdColabHeader.getOrNull()!!
            val osOpenList = osList.filter { os ->
                val header = headerList.firstOrNull { h -> h.idOS == os.idOS }
                header == null || (header.idColab == idColab && header.status != Status.FINISH)
            }
            val idOSHeaderByIdColab = headerList.filter { it.idColab == idColab }.map { it.idOS }
            val list = osOpenList.map { os ->
                val plant = plantList.first { it.idPlant == os.idPlantOS }
                val status = idOSHeaderByIdColab.contains(os.idOS)
                OSScreenModel(
                    idOS = os.idOS,
                    nroOS = os.nroOS,
                    period = os.descPeriodOS,
                    codPlant = plant.codPlant,
                    descPlant = plant.descPlant,
                    status = status
                )
            }
            val listOrder = list.sortedWith(
                compareBy( { it.status }, { it.idOS } )
            )
            return Result.success(listOrder)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}