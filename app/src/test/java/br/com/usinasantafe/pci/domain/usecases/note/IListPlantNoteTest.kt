package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.presenter.model.PlantScreenModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListPlantNoteTest {

    private val itemRepository = mock<ItemRepository>()
    private val plantRepository = mock<PlantRepository>()
    private val usecase = IListPlantNote(
        itemRepository = itemRepository,
        plantRepository = plantRepository
    )

    @Test
    fun `Check return failure if have error in ItemRepository listAll`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IItemRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IItemRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository getById`() =
        runTest {
            val itemList = listOf(
                Item(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                Item(
                    idItem = 2,
                    seqItem = 2,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                Item(
                    idItem = 3,
                    seqItem = 3,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 2,
                    idServiceItem = 2
                ),
                Item(
                    idItem = 4,
                    seqItem = 4,
                    idOSItem = 1,
                    idPlantItem = 3,
                    idComponentItem = 3,
                    idServiceItem = 3
                ),
                Item(
                    idItem = 5,
                    seqItem = 5,
                    idOSItem = 1,
                    idPlantItem = 3,
                    idComponentItem = 3,
                    idServiceItem = 4
                )
            )
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPlantNote -> IPlantRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return success if process execute success`() =
        runTest {
            val itemList = listOf(
                Item(
                    idItem = 1,
                    seqItem = 1,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                Item(
                    idItem = 2,
                    seqItem = 2,
                    idOSItem = 1,
                    idPlantItem = 1,
                    idComponentItem = 1,
                    idServiceItem = 1
                ),
                Item(
                    idItem = 3,
                    seqItem = 3,
                    idOSItem = 1,
                    idPlantItem = 2,
                    idComponentItem = 2,
                    idServiceItem = 2
                ),
                Item(
                    idItem = 4,
                    seqItem = 4,
                    idOSItem = 1,
                    idPlantItem = 3,
                    idComponentItem = 3,
                    idServiceItem = 3
                ),
                Item(
                    idItem = 5,
                    seqItem = 5,
                    idOSItem = 1,
                    idPlantItem = 3,
                    idComponentItem = 3,
                    idServiceItem = 3
                )
            )
            val plantList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "1",
                    descPlant = "1",
                    idFactorySectionPlant = 1
                ),
                Plant(
                    idPlant = 2,
                    codPlant = "2",
                    descPlant = "2",
                    idFactorySectionPlant = 1
                ),
                Plant(
                    idPlant = 3,
                    codPlant = "3",
                    descPlant = "3",
                    idFactorySectionPlant = 1
                )
            )
            val plantScreenList = listOf(
                PlantScreenModel(
                    id = 1,
                    cod = "1",
                    desc = "1"
                ),
                PlantScreenModel(
                    id = 2,
                    cod = "2",
                    desc = "2"
                ),
                PlantScreenModel(
                    id = 3,
                    cod = "3",
                    desc = "3"
                )
            )
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                Result.success(itemList)
            )
            val ids = listOf(1, 2, 3)
            whenever(
                plantRepository.listByIds(ids)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                plantScreenList
            )
        }

}