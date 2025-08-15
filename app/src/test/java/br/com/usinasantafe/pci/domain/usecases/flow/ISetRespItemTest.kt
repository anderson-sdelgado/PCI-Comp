package br.com.usinasantafe.pci.domain.usecases.flow

import br.com.usinasantafe.pci.domain.entities.variable.Resp
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.OptionResp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetRespItemTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ISetRespItem(
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository saveResp`() =
        runTest {
            val modelCaptor = argumentCaptor<Resp>().apply {
                whenever(
                    checkListRepository.saveResp(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "ICheckListRepository.saveResp",
                        "-",
                        Exception()
                    )
                )
            }
            val result = usecase(
                id = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "obs"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItem -> ICheckListRepository.saveResp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val entity = modelCaptor.firstValue
            assertEquals(
                entity.idItem,
                1
            )
            assertEquals(
                entity.idPlant,
                1
            )
            assertEquals(
                entity.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                entity.obs,
                "obs"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val modelCaptor = argumentCaptor<Resp>().apply {
                whenever(
                    checkListRepository.saveResp(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            val result = usecase(
                id = 1,
                idPlant = 1,
                option = OptionResp.NON_CONFORMING,
                obs = "obs"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val entity = modelCaptor.firstValue
            assertEquals(
                entity.idItem,
                1
            )
            assertEquals(
                entity.idPlant,
                1
            )
            assertEquals(
                entity.option,
                OptionResp.NON_CONFORMING
            )
            assertEquals(
                entity.obs,
                "obs"
            )
        }

}