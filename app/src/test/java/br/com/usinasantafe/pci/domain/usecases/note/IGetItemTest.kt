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

class IGetItemTest {

    private val itemRepository = mock<ItemRepository>()
    private val serviceRepository = mock<ServiceRepository>()
    private val componentRepository = mock<ComponentRepository>()
    private val usecase = IGetItem(
        itemRepository = itemRepository,
        serviceRepository = serviceRepository,
        componentRepository = componentRepository
    )

    @Test
    fun `Check return failure if have error in ItemRepository getById`() =
        runTest {
            whenever(
                itemRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IItemRepository.getById",
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
                "IGetItem -> IItemRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ServiceRepository getById`() =
        runTest {
            whenever(
                itemRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Item(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1,
                    )
                )
            )
            whenever(
                serviceRepository.getById(1)
            ).thenReturn(
                resultFailure(
                    "IServiceRepository.getById",
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
                "IGetItem -> IServiceRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and idComponentItem is 0`() =
        runTest {
            whenever(
                itemRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Item(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 0,
                        idServiceItem = 1,
                    )
                )
            )
            whenever(
                serviceRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Service(
                        idService = 1,
                        codService = 1,
                        descService = "Service",
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Service"
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository getById`() =
        runTest {
            whenever(
                itemRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Item(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 3,
                        idServiceItem = 2,
                    )
                )
            )
            whenever(
                serviceRepository.getById(2)
            ).thenReturn(
                Result.success(
                    Service(
                        idService = 2,
                        codService = 2,
                        descService = "Service",
                    )
                )
            )
            whenever(
                componentRepository.getById(3)
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.getById",
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
                "IGetItem -> IComponentRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Item(
                        idItem = 1,
                        seqItem = 1,
                        idOSItem = 1,
                        idPlantItem = 1,
                        idComponentItem = 3,
                        idServiceItem = 2,
                    )
                )
            )
            whenever(
                serviceRepository.getById(2)
            ).thenReturn(
                Result.success(
                    Service(
                        idService = 2,
                        codService = 2,
                        descService = "Service",
                    )
                )
            )
            whenever(
                componentRepository.getById(3)
            ).thenReturn(
                Result.success(
                    Component(
                        idComponent = 3,
                        codComponent = "03",
                        descComponent = "Component",
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Service\n03 - Component"
            )
        }

}