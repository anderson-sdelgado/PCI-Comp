package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListItemNoteTest {

    private val itemRepository = mock<ItemRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val componentRepository = mock<ComponentRepository>()
    private val serviceRepository = mock<ServiceRepository>()
    private val usecase = IListItemNote(
        itemRepository = itemRepository,
        checkListRepository = checkListRepository,
        componentRepository = componentRepository,
        serviceRepository = serviceRepository
    )

    private val itemList = listOf(
        Item(
            idItem = 1,
            seqItem = 1,
            idOSItem = 1,
            idPlantItem = 1,
            idComponentItem = 0,
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

    @Test
    fun `Check return failure if have error in CheckListRepository getIdOSHeaderOpen`() =
        runTest {
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getIdOSHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote -> ICheckListRepository.getIdOSHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemRepository listByIdOSAndIdPlant`() =
        runTest {
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                resultFailure(
                    "IItemRepository.listByIdOSAndIdPlant",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote -> IItemRepository.listByIdOSAndIdPlant"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository listByIdList`() =
        runTest {
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                componentRepository.listByIds(listOf(1, 2, 3))
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote -> IComponentRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ServiceRepository listByIdList`() =
        runTest {
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                componentRepository.listByIds(listOf(1, 2, 3))
            ).thenReturn(
                Result.success(
                    listOf(
                        Component(
                            idComponent = 1,
                            codComponent = "1",
                            descComponent = "Component 1"
                        ),
                        Component(
                            idComponent = 2,
                            codComponent = "2",
                            descComponent = "Component 2"
                        ),
                        Component(
                            idComponent = 3,
                            codComponent = "3",
                            descComponent = "Component 3"
                        )
                    )
                )
            )
            whenever(
                serviceRepository.listByIds(listOf(1, 2, 3, 4))
            ).thenReturn(
                resultFailure(
                    "IServiceRepository.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemNote -> IServiceRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return list if process execute success`() =
        runTest {
            whenever(
                checkListRepository.getIdOSHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                itemRepository.listByIdOSAndIdPlant(
                    idOS = 1,
                    idPlant = 1
                )
            ).thenReturn(
                Result.success(itemList)
            )
            whenever(
                componentRepository.listByIds(listOf(1, 2, 3))
            ).thenReturn(
                Result.success(
                    listOf(
                        Component(
                            idComponent = 1,
                            codComponent = "01",
                            descComponent = "Component 1"
                        ),
                        Component(
                            idComponent = 2,
                            codComponent = "02",
                            descComponent = "Component 2"
                        ),
                        Component(
                            idComponent = 3,
                            codComponent = "03",
                            descComponent = "Component 3"
                        )
                    )
                )
            )
            whenever(
                serviceRepository.listByIds(listOf(1, 2, 3, 4))
            ).thenReturn(
                Result.success(
                    listOf(
                        Service(
                            idService = 1,
                            codService = 1,
                            descService = "Service 1"
                        ),
                        Service(
                            idService = 2,
                            codService = 2,
                            descService = "Service 2"
                        ),
                        Service(
                            idService = 3,
                            codService = 3,
                            descService = "Service 3"
                        ),
                        Service(
                            idService = 4,
                            codService = 4,
                            descService = "Service 4"
                        )
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                5
            )
            val itemScreenModel1 = list[0]
            assertEquals(
                itemScreenModel1.id,
                1
            )
            assertEquals(
                itemScreenModel1.pos,
                "Questão 1"
            )
            assertEquals(
                itemScreenModel1.descService,
                "Service 1"
            )
            assertEquals(
                itemScreenModel1.descComponent,
                ""
            )
            val itemScreenModel2 = list[1]
            assertEquals(
                itemScreenModel2.id,
                2
            )
            assertEquals(
                itemScreenModel2.pos,
                "Questão 2"
            )
            assertEquals(
                itemScreenModel2.descService,
                "Service 1"
            )
            assertEquals(
                itemScreenModel2.descComponent,
                "01 - Component 1"
            )
            val itemScreenModel3 = list[2]
            assertEquals(
                itemScreenModel3.id,
                3
            )
            assertEquals(
                itemScreenModel3.pos,
                "Questão 3"
            )
            assertEquals(
                itemScreenModel3.descService,
                "Service 2"
            )
            assertEquals(
                itemScreenModel3.descComponent,
                "02 - Component 2"
            )
            val itemScreenModel4 = list[3]
            assertEquals(
                itemScreenModel4.id,
                4
            )
            assertEquals(
                itemScreenModel4.pos,
                "Questão 4"
            )
            assertEquals(
                itemScreenModel4.descService,
                "Service 3"
            )
            assertEquals(
                itemScreenModel4.descComponent,
                "03 - Component 3"
            )
            val itemScreenModel5 = list[4]
            assertEquals(
                itemScreenModel5.id,
                5
            )
            assertEquals(
                itemScreenModel5.pos,
                "Questão 5"
            )
            assertEquals(
                itemScreenModel5.descService,
                "Service 4"
            )
            assertEquals(
                itemScreenModel5.descComponent,
                "03 - Component 3"
            )
        }
}