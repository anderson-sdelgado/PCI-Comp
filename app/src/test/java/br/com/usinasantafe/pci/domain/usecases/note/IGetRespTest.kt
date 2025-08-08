package br.com.usinasantafe.pci.domain.usecases.note

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.entities.stable.Item
import br.com.usinasantafe.pci.domain.entities.stable.Service
import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ItemRepository
import br.com.usinasantafe.pci.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.presenter.model.RespScreenModel
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IGetRespTest {

    private val itemRepository = mock<ItemRepository>()
    private val serviceRepository = mock<ServiceRepository>()
    private val componentRepository = mock<ComponentRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = IGetResp(
        itemRepository = itemRepository,
        serviceRepository = serviceRepository,
        componentRepository = componentRepository,
        checkListRepository = checkListRepository
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
                "IGetResp -> IItemRepository.getById"
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
                "IGetResp -> IServiceRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository getRespByIdItem and idComponentItem is 0`() =
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
                        descService = "Service 1",
                    )
                )
            )
            whenever(
                checkListRepository.getRespByIdItem(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getRespByIdItem",
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
                "IGetResp -> ICheckListRepository.getRespByIdItem"
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
                        descService = "Service 1",
                    )
                )
            )
            whenever(
                checkListRepository.getRespByIdItem(1)
            ).thenReturn(
                Result.success(
                    Resp(
                        id = 1,
                        idHeader = 1,
                        idItem = 1,
                        option = OptionResp.ACCORDING,
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
                RespScreenModel(
                    pos = 1,
                    desc = "Service 1",
                    option = OptionResp.ACCORDING,
                    obs = null
                )
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository getById and idComponentItem is not 0`() =
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
                        idComponentItem = 1,
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
                        descService = "Service 1",
                    )
                )
            )
            whenever(
                componentRepository.getById(1)
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
                "IGetResp -> IComponentRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository getRespByIdItem and idComponentItem is not 0`() =
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
                        idComponentItem = 1,
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
                        descService = "Service 1",
                    )
                )
            )
            whenever(
                componentRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Component(
                        idComponent = 1,
                        codComponent = "01",
                        descComponent = "Component 1",
                    )
                )
            )
            whenever(
                checkListRepository.getRespByIdItem(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getRespByIdItem",
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
                "IGetResp -> ICheckListRepository.getRespByIdItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and idComponentItem is not 0`() =
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
                        idComponentItem = 1,
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
                        descService = "Service 1",
                    )
                )
            )
            whenever(
                componentRepository.getById(1)
            ).thenReturn(
                Result.success(
                    Component(
                        idComponent = 1,
                        codComponent = "01",
                        descComponent = "Component 1",
                    )
                )
            )
            whenever(
                checkListRepository.getRespByIdItem(1)
            ).thenReturn(
                Result.success(
                    Resp(
                        id = 1,
                        idHeader = 1,
                        idItem = 1,
                        option = OptionResp.NON_CONFORMING,
                        obs = "Obs Test"
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
                RespScreenModel(
                    pos = 1,
                    desc = "Service 1\n01 - Component 1",
                    option = OptionResp.NON_CONFORMING,
                    obs = "Obs Test"
                )
            )
        }

}