package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckItemNoteTest {

    private val itemRepository = mock<ItemRepository>()
    private val componentRepository = mock<ComponentRepository>()
    private val serviceRepository = mock<ServiceRepository>()
    private val usecase = ICheckItemNote(
        itemRepository = itemRepository,
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
    fun `Check return failure if have error in ItemRepositoryRepository listAll`() =
        runTest {
            whenever(
                itemRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "IItemRepositoryRepository.listAll",
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
                "ICheckItemNote -> IItemRepositoryRepository.listAll"
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
                itemRepository.listAll()
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
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckItemNote -> IComponentRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if qtd component is different in qtd component of item list`() =
        runTest {
            whenever(
                itemRepository.listAll()
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
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return failure if have error in ServiceRepository listByIdList`() =
        runTest {
            whenever(
                itemRepository.listAll()
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
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckItemNote -> IServiceRepository.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if qtd service is different in qtd service of item list`() =
        runTest {
            whenever(
                itemRepository.listAll()
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
                Result.success(
                    listOf(
                        Service(
                            idService = 1,
                            codService = 1,
                            descService = "1"
                        ),
                        Service(
                            idService = 2,
                            codService = 2,
                            descService = "2"
                        ),
                        Service(
                            idService = 3,
                            codService = 3,
                            descService = "3"
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return true if process execute success`() =
        runTest {
            whenever(
                itemRepository.listAll()
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
                Result.success(
                    listOf(
                        Service(
                            idService = 1,
                            codService = 1,
                            descService = "1"
                        ),
                        Service(
                            idService = 2,
                            codService = 2,
                            descService = "2"
                        ),
                        Service(
                            idService = 3,
                            codService = 3,
                            descService = "3"
                        ),
                        Service(
                            idService = 4,
                            codService = 4,
                            descService = "4"
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }
}