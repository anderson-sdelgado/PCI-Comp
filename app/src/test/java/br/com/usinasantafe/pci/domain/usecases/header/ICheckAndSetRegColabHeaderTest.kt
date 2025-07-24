package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.entities.stable.Colab
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.junit.Test
import org.mockito.kotlin.whenever

class ICheckAndSetRegColabHeaderTest {

    private val getToken = mock<GetToken>()
    private val colabRepository = mock<ColabRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = ICheckAndSetRegColabHeader(
        getToken = getToken,
        colabRepository = colabRepository,
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> GetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if reg is incorrect`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            val result = usecase("19759a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository getByRegColab`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.getByRegColab(
                    token = "token",
                    regColab = 19759
                )
            ).thenReturn(
                resultFailure(
                    "IColabRepository.getByRegColab",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IColabRepository.getByRegColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if function execute successfully and colab is invalid`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.getByRegColab(
                    token = "token",
                    regColab = 19759
                )
            ).thenReturn(
                Result.success(
                    Colab(
                        idColab = 0,
                        regColab = 0,
                        nameColab = "INVALID",
                        idFactorySectionColab = 0
                    )
                )
            )
            val result = usecase("19759")
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
    fun `Check return failure if have error in ColabRepository add`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.getByRegColab(
                    token = "token",
                    regColab = 19759
                )
            ).thenReturn(
                Result.success(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            )
            whenever(
                colabRepository.add(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IColabRepository.add",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> IColabRepository.add"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository setIdColabHeader`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.getByRegColab(
                    token = "token",
                    regColab = 19759
                )
            ).thenReturn(
                Result.success(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            )
            whenever(
                colabRepository.add(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.setIdColabHeader(1)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.setIdColabHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckAndSetRegColabHeader -> ICheckListRepository.setIdColabHeader"
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
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.getByRegColab(
                    token = "token",
                    regColab = 19759
                )
            ).thenReturn(
                Result.success(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            )
            whenever(
                colabRepository.add(
                    Colab(
                        idColab = 1,
                        regColab = 19759,
                        nameColab = "ANDERSON DA SILVA DELGADO",
                        idFactorySectionColab = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.setIdColabHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("19759")
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